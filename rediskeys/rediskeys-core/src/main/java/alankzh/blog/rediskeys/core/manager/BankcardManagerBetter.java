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
 * 另一种缓存策略
 * 孤立的key
 */
@Service
@Slf4j
public class BankcardManagerBetter {

    private static final String KEY_CUSTOMERID_PREFIX = "alankzh:bankcard:cid:";
    private static final String KEY_INDEXCARDID_PREFIX = "alankzh:bankcard:idxcardid:";

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
        List<String> needDelList = new ArrayList<>(2);
        needDelList.add(key1);
        needDelList.add(key2);
        redisOperationUtil.deleteAll(needDelList);

        return ef;
    }

    public List<BankcardEntity> findByCustomerId(String customerId) {
        Assert.notNull(customerId, "fffffffffff cid");

        String key1 = KEY_CUSTOMERID_PREFIX + customerId;
        String value = redisOperationUtil.get(key1);
        if (value != null){
            return JsonUtil.listFromJson(value, BankcardEntity.class);
        }

        Map<String, String> needSetMap = new HashMap<>();
        List<BankcardEntity> list = bankcardMapper.findByCustomerId(customerId);

        // 这里可选择是否放置key-single到缓存中, 这里演示为放置.
        for (int i=0; i<list.size(); i++){
            String key2 = KEY_INDEXCARDID_PREFIX + list.get(i).getIndexCardId();
            needSetMap.put(key2, JsonUtil.toJson(list.get(i)));
        }
        needSetMap.put(key1, JsonUtil.toJson(list));

        redisOperationUtil.multiSet(needSetMap);

        return list;
    }

    public BankcardEntity findByIndexCardId(String indexCardId) {
        Assert.notNull(indexCardId, "fffffffffffff idx");

        String key2 = KEY_INDEXCARDID_PREFIX + indexCardId;
        String value = redisOperationUtil.get(key2);
        if (!StringUtils.isEmpty(value)){
            return JsonUtil.fromJson(value, BankcardEntity.class);
        }

        BankcardEntity bankcardEntity = bankcardMapper.findByIndexCardId(indexCardId);
        if (bankcardEntity == null){
            redisOperationUtil.set(key2, EMPTY);
            return null;
        }

        // 不去关联 key-multi
        redisOperationUtil.set(key2, JsonUtil.toJson(bankcardEntity));

        return bankcardEntity;
    }

    /**
     * 更新策略, 由于key之间孤立, 故需要库中每行数据作为key的关联.
     * 于是更新有两种策略:
     *  1. 底层方法删除之前查询到相应的数据. 取出数据中的key, 更新完成后, 以数据中的字段值来删除缓存的key
     *     大部分表层service, 在更新前往往会触发对相应数据的查询和校验, 这时对相应数据的再次获取, 往往是从缓存中获取.
     *
     *  2. 更新操作, 对涉及到的数据, 必须传入作为redis的key的字段值. 否则抛异常.
     *      如果大部分表层service真的在更新前触发相应查询, 那么这些key作为必传, 并不让人为难.
     *
     *  这里演示第二种方案
     */
    public int updateInformationByIndexCardId(String information, String indexCardId, String customerId) {
        Assert.notNull(indexCardId, "ffffffffff idx");
        Assert.notNull(customerId, "fffffffff cid");

        int ef = bankcardMapper.updateInformationByIndexCardId(information, indexCardId);
        if (ef <= 0){
            return ef;
        }

        String key1 = KEY_CUSTOMERID_PREFIX + customerId;
        String key2 = KEY_INDEXCARDID_PREFIX + indexCardId;
        List<String> needDelList = new ArrayList<>(2);
        needDelList.add(key1);
        needDelList.add(key2);

        redisOperationUtil.deleteAll(needDelList);

        return ef;
    }

    public int updateInformationByCid(String information, List<BankcardEntity> customerIdList) {
        Assert.notNull(customerIdList, "???????????");

        String customerId = customerIdList.get(0).getCustomerId();
        Assert.notNull(customerId, "fffffffffffff cid");
        int ef = bankcardMapper.updateInformationByCustomerId(information, customerId);
        if (ef <= 0){
            return ef;
        }

        List<String> needDelList = new ArrayList<>(customerIdList.size() + 1);
        needDelList.add(KEY_CUSTOMERID_PREFIX + customerId);
        for (int i=0; i<customerIdList.size(); i++){
            BankcardEntity effectiveBankcard = customerIdList.get(i);
            Assert.notNull(effectiveBankcard.getIndexCardId(), "fffffffffff idx");
            Assert.isTrue(customerId.equals(effectiveBankcard.getCustomerId()), "fffffffff cid must be equals");

            String key2 = KEY_INDEXCARDID_PREFIX + effectiveBankcard.getIndexCardId();
            needDelList.add(key2);
        }

        redisOperationUtil.deleteAll(needDelList);

        return ef;
    }

}
