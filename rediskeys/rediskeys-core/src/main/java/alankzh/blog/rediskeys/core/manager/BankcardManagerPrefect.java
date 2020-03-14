package alankzh.blog.rediskeys.core.manager;

import alankzh.blog.base.core.jacson.JsonUtil;
import alankzh.blog.rediskeys.core.BankcardEntity;
import alankzh.blog.rediskeys.core.RedisOperationUtil;
import alankzh.blog.rediskeys.core.dao.BankcardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * key-keys方式的一种正确实现
 */
@Service
@Slf4j
public class BankcardManagerPrefect {

    // 一个cid可能对应多张卡 即KEY_CID 属于 key-multi
    private static final String KEY_CUSTOMERID_PREFIX = "alankzh:bankcard:cid:";
    // 一张卡假设只能绑定在一个人身上 即KEY_IDXCARDID 属于 key-single
    private static final String KEY_INDEXCARDID_PREFIX = "alankzh:bankcard:idxcardid:";

    // key_keys 用于存储cid做key, 相应的其余关联数据在缓存中的key
    private static final String KEY_CID_IDX_KEYS = "alankzh:bankcard:keys:";

    // 反向关联 idx_id 和 cid, 使得删除idx_id时,能找到相应的cid
    private static final String KEY_IDX_CID_REVERSE = "alankzh:bankcard:rev:";

    private static final String KEY_KEYS_SPLIT = ",";

    private static final String EMPTY = "";

    @Autowired
    private RedisOperationUtil redisOperationUtil;

    @Autowired
    private BankcardMapper bankcardMapper;

    public int insert(BankcardEntity bankcardEntity) {
        Assert.notNull(bankcardEntity.getCustomerId(), "fffffff cid");
        Assert.notNull(bankcardEntity.getIndexCardId(), "fffffff idx");
        Assert.notNull(bankcardEntity.getMobile(), "fffffff mobile");

        int ef = bankcardMapper.insert(bankcardEntity);

        String key1 = KEY_CUSTOMERID_PREFIX + bankcardEntity.getCustomerId();
        String key2 = KEY_INDEXCARDID_PREFIX + bankcardEntity.getIndexCardId();
        String keyKeys = KEY_CID_IDX_KEYS + bankcardEntity.getCustomerId();

        List<String> needDelList = new ArrayList<>();
        needDelList.add(key1);
        needDelList.add(key2);
        needDelList.add(keyKeys);
        String keys = redisOperationUtil.get(keyKeys);
        if (keys != null) {
            needDelList.addAll(Arrays.asList(keys.split(KEY_KEYS_SPLIT)));
        }
        redisOperationUtil.deleteAll(needDelList);

        return ef;
    }

    public List<BankcardEntity> findByCustomerId(String customerId) {
        Assert.notNull(customerId, "fffff cid");

        String key1 = KEY_CUSTOMERID_PREFIX + customerId;
        String value = redisOperationUtil.get(key1);
        if (value != null){
            return JsonUtil.listFromJson(value, BankcardEntity.class);
        }
        List<BankcardEntity> list = bankcardMapper.findByCustomerId(customerId);
        redisOperationUtil.set(key1, JsonUtil.toJson(list));
        return list;
    }

    public BankcardEntity findByIndexCardId(String indexCardId) {
        Assert.notNull(indexCardId, "fffffff idx");

        String key2 = KEY_INDEXCARDID_PREFIX + indexCardId;
        log.info(key2);
        String value = redisOperationUtil.get(key2);
        log.info(value);
        if (!StringUtils.isEmpty(value)) {
            return JsonUtil.fromJson(value, BankcardEntity.class);
        }
        if (EMPTY.equals(value)){
            return null;
        }

        BankcardEntity bankcardEntity = bankcardMapper.findByIndexCardId(indexCardId);
        if (bankcardEntity == null) {
            /**
             * 争议性操作 实际上,业务查询为空后, 很少会对同样的key再次查询
             * 但可能会遇到针对一定为空的key, 进行的请求攻击
             */
            redisOperationUtil.set(key2, EMPTY);
            return null;
        }

        Map<String, String> needSetMap = new HashMap<>();

        /**
         * 对key-single的查询
         *
         * 1. 值放入key-single
         * 2. 将key-single自身的key值追加放入key-keys
         * 3. 值对应的key-multi放入key-rev
         */
        String keyKeys = KEY_CID_IDX_KEYS + bankcardEntity.getCustomerId();
        String keyRev = KEY_IDX_CID_REVERSE + bankcardEntity.getIndexCardId();
        needSetMap.put(key2, JsonUtil.toJson(bankcardEntity));
        needSetMap.put(keyRev, bankcardEntity.getCustomerId());

        StringBuilder strbder = new StringBuilder();
        strbder.append(key2)
                .append(KEY_KEYS_SPLIT)
                .append(keyRev);
        String keys = redisOperationUtil.get(keyKeys);
        if (StringUtils.isEmpty(keys)) {
            needSetMap.put(keyKeys, strbder.toString());
        } else {
            needSetMap.put(keyKeys, keys + KEY_KEYS_SPLIT + strbder.toString());
        }
        redisOperationUtil.multiSet(needSetMap);

        return bankcardEntity;
    }

    public int updateInformationByIndexCardId(String information, String indexCardId) {
        Assert.notNull(information, "fffffff information");
        Assert.notNull(information, "fffffff information");

        int ef = bankcardMapper.updateInformationByIndexCardId(information, indexCardId);

        if (ef <= 0){
            return ef;
        }

        List<String> needDelList = new ArrayList<>();

        String keyRev = KEY_IDX_CID_REVERSE + indexCardId;

        String cid = redisOperationUtil.get(keyRev);

        if (cid != null){
            log.info("cid:{}", cid);
            String key1 = KEY_CUSTOMERID_PREFIX + cid;
            String keyKeys = KEY_CID_IDX_KEYS + cid;
            /**
             * 这里实际有两种做法,
             * 1. 删去cid关联的全部idxCardId的缓存,删去cid本身的缓存,删去keyKeys
             * 2. 删去cid本身缓存,更正keyKeys,去除连缀的idxCardId中与参数相同的idx.
             *
             * 第二种看起来更精微正确,实际做多错多,对keyKeys的更多操作,会引来更多的race condition
             * 这里仅演示第一种做法.
             */
            needDelList.add(key1);
            needDelList.add(keyKeys);
            String keys = redisOperationUtil.get(keyKeys);
            if (keys != null){
                needDelList.addAll(Arrays.asList(keys.split(KEY_KEYS_SPLIT)));
            }
        } else {
            String key2 = KEY_INDEXCARDID_PREFIX + indexCardId;
            needDelList.add(key2);
            needDelList.add(keyRev);
        }

        redisOperationUtil.deleteAll(needDelList);

        return ef;
    }

    public int updateInformationByCid(String information, String customerId) {
        Assert.notNull(information, "fffffff info");
        Assert.notNull(customerId, "fffffffff cid");

        int ef = bankcardMapper.updateInformationByCustomerId(information, customerId);

        if (ef <= 0){
            return ef;
        }

        List<String> needDelList = new ArrayList<>();

        String key1 = KEY_CUSTOMERID_PREFIX + customerId;
        String keyKeys = KEY_CID_IDX_KEYS + customerId;
        needDelList.add(key1);
        needDelList.add(keyKeys);

        String keys = redisOperationUtil.get(keyKeys);
        if (keys != null){
            needDelList.addAll(Arrays.asList(keys.split(KEY_KEYS_SPLIT)));
        }
        redisOperationUtil.deleteAll(needDelList);

        return ef;
    }

    /**
     * 复杂性场景,
     * 更新与缓存key相关的字段
     * 此场景下需要对旧key做删除,新key也做删除.
     */
    public int updateCustomerIdByIdxCardId(){
        throw new RuntimeException("waiting to complete");
    }


}


