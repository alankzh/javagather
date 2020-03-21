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

@Service
@Slf4j
public class BankcardManagerMaster {
    private static final String KEY_CUSTOMERID_PREFIX = "alankzh:bankcard:cid:";
    private static final String KEY_INDEXCARDID_PREFIX = "alankzh:bankcard:idxcardid:";
    private static final String KEY_CID_IDX_KEYS = "alankzh:bankcard:keys:";
    private static final String KEY_IDX_CID_REVERSE = "alankzh:bankcard:rev:";
    private static final String EMPTY = "";

    @Autowired
    private BankcardMapper bankcardMapper;

    @Autowired
    private RedisOperationUtil redisOperationUtil;

    public int insert(BankcardEntity bankcardEntity) {
        Assert.notNull(bankcardEntity.getCustomerId(), "fffffff cid");
        Assert.notNull(bankcardEntity.getIndexCardId(), "fffffff idx");
        Assert.notNull(bankcardEntity.getMobile(), "fffffff mobile");

        int ef = bankcardMapper.insert(bankcardEntity);

        String keyMulti = KEY_CUSTOMERID_PREFIX + bankcardEntity.getCustomerId();
        String keySingle = KEY_INDEXCARDID_PREFIX + bankcardEntity.getIndexCardId();
        String hash = KEY_CID_IDX_KEYS + bankcardEntity.getCustomerId();

        redisOperationUtil.hdelete(hash, keySingle);
        redisOperationUtil.delete(keyMulti);
        redisOperationUtil.delete(keySingle);

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

        Map<String, String> map = new HashMap<>();
        Map<String, String> hmap = new HashMap<>();
        for (int i=0; i<list.size(); i++){
            BankcardEntity b = list.get(i);
            String keyRev = KEY_IDX_CID_REVERSE + b.getIndexCardId();
            hmap.put(keyRev, EMPTY);
            map.put(keyRev, customerId);
        }
        map.put(key1, JsonUtil.toJson(list));
        redisOperationUtil.multiSet(map);

        String hash = KEY_CID_IDX_KEYS + customerId;
        redisOperationUtil.hputAll(hash, hmap);

        return list;
    }

    public BankcardEntity findByIndexCardId(String indexCardId) {
        Assert.notNull(indexCardId, "fffffff idx");

        String key2 = KEY_INDEXCARDID_PREFIX + indexCardId;
        String value = redisOperationUtil.get(key2);
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

        String keySingle = KEY_INDEXCARDID_PREFIX + bankcardEntity.getIndexCardId();
        String hash = KEY_CID_IDX_KEYS + bankcardEntity.getCustomerId();
        String keyRev = KEY_IDX_CID_REVERSE + bankcardEntity.getIndexCardId();

        redisOperationUtil.set(keySingle, JsonUtil.toJson(bankcardEntity));
        redisOperationUtil.hput(hash, keySingle, EMPTY);
        redisOperationUtil.hput(hash, keyRev, EMPTY);

        return bankcardEntity;
    }

    public int updateInformationByIndexCardId(String information, String indexCardId) {
        Assert.notNull(information, "fffffff information");
        Assert.notNull(information, "fffffff information");

        int ef = bankcardMapper.updateInformationByIndexCardId(information, indexCardId);

        if (ef <= 0){
            return ef;
        }

        String keySingle = KEY_INDEXCARDID_PREFIX + indexCardId;
        String keyRev = KEY_IDX_CID_REVERSE + indexCardId;
        String keyMulti = redisOperationUtil.get(keyRev);
        List<String> needDelList = new ArrayList<>();
        needDelList.add(keySingle);
        needDelList.add(keyRev);
        if (keyMulti != null){
            needDelList.add(KEY_CUSTOMERID_PREFIX + keyMulti);
            String hash = KEY_CID_IDX_KEYS + keyMulti;
            redisOperationUtil.hdeleteAll(hash, new String[]{keySingle, keyRev});
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

        String hash = KEY_CID_IDX_KEYS + customerId;
        String keyMulti = KEY_CUSTOMERID_PREFIX + customerId;
        Set<Object> keys = redisOperationUtil.hkeys(hash);
        log.info(keys.toString());
        List<String> needDelList = new ArrayList<>();
        needDelList.add(keyMulti);
        String[] hneedDel = new String[keys.size()];
        int i=0;
        for (Object o : keys){
            String hkey = (String) o;
            needDelList.add(hkey);
            hneedDel[i] = hkey;
            i++;
        }
        redisOperationUtil.deleteAll(needDelList);
        if (hneedDel.length>0){
            redisOperationUtil.hdeleteAll(hash, hneedDel);
        }


        return ef;
    }

}
