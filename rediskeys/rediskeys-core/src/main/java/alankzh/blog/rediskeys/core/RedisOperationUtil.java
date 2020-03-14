package alankzh.blog.rediskeys.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisOperationUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${cache.expire.minutes}")
    private Long expireMinutes;

    public String get(String key){
        log.info("redis get key: {}", key);
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value) {
        log.info("redis set key:{}, value:{}", key, value);
        redisTemplate.opsForValue().set(key, value, expireMinutes, TimeUnit.MINUTES);
    }

    public void multiSet(Map<String, String> map){
        log.info("redis multi set, map:{}", map);
        redisTemplate.opsForValue().multiSet(map);
    }

    public void delete(String key) {
        log.info("redis delete key: {}", key);
        redisTemplate.delete(key);
    }

    public void deleteAll(List<String> keys){
        log.info("redis delete all keys:{}", keys);
        redisTemplate.delete(keys);
    }


}
