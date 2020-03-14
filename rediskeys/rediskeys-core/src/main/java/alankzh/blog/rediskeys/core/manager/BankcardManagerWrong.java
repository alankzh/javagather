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
public class BankcardManagerWrong {

    @Autowired
    private RedisOperationUtil redisOperationUtil;

    @Autowired
    private BankcardMapper bankcardMapper;

    private static final String KEY_CUSTOMERID_PREFIX = "alankzh:bankcard:cid:";
    private static final String KEY_INDEXCARDID_PREFIX = "alankzh:bankcard:idxcardid:";
    private static final String KEY_CID_IDX_KEYS = "alankzh:bankcard:keys:";
    private static final String KEY_IDX_CID_REVERSE = "alankzh:bankcard:rev:";

    private static final String EMPTY = "";

    /**
     * 误用key-keys 之一
     * 将key-single的查询值设置到key-multi中去了
     */
    public BankcardEntity findByIndexCardId(String indexCardId){
        Assert.notNull(indexCardId, "ffffffffff idx");

        String key2 = KEY_INDEXCARDID_PREFIX + indexCardId;
        String value = redisOperationUtil.get(key2);

        if ( !StringUtils.isEmpty(value)){
            return JsonUtil.fromJson(value, BankcardEntity.class);
        }
        if (EMPTY.equals(value)){
            return null;
        }

        BankcardEntity bankcardEntity = bankcardMapper.findByIndexCardId(indexCardId);
        if (bankcardEntity == null){
            redisOperationUtil.set(key2, EMPTY);
            return null;
        }

        List<String> list = new ArrayList<>();
        list.add(JsonUtil.toJson(bankcardEntity));
        String key1 = KEY_CUSTOMERID_PREFIX + bankcardEntity.getCustomerId();
        redisOperationUtil.set(key1, JsonUtil.toJson(list));
        redisOperationUtil.set(key2, JsonUtil.toJson(bankcardEntity));

        return bankcardEntity;
    }

    /**
     * 误用key-keys之一
     * 更新时仅仅删除了key-keys, 未删除后缀的key-single值
     */
    public int updateInformationByCustomerId(String information, String customerId){
        Assert.notNull(customerId, "ffffffff idx");

        int ef = bankcardMapper.updateInformationByIndexCardId(information, customerId);
        if (ef <= 0){
            return ef;
        }
        String key1 = KEY_CUSTOMERID_PREFIX + customerId;
        String keyKeys = KEY_CID_IDX_KEYS + customerId;
        List<String> needDelList = new ArrayList<>();
        needDelList.add(key1);
        needDelList.add(keyKeys);

        redisOperationUtil.deleteAll(needDelList);

        return ef;
    }

    /**
     * 灾难性场景
     * 不知道更新哪个字段, 以及依据哪个字段更新
     * 逻辑分支太多.
     */
    @Deprecated
    public int updateData(BankcardEntity bankcardEntity){
        throw new RuntimeException("bad method");
    }

    /**
     * 灾难性场景2
     * 根据哪个字段查询?
     * 这种提供api的方式, 还允许了非索引查询
     */
    @Deprecated
    public List<BankcardEntity> queryForList(BankcardEntity bankcardEntity){
        throw new RuntimeException("bad method");
    }


}
