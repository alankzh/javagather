package alankzh.blog.rediskeys.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {


    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setTimeout(20000);

        jedisConnectionFactory.setHostName("172.17.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("123456");

        return jedisConnectionFactory;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲
        jedisPoolConfig.setMaxIdle(300);
        // 最大链接
        jedisPoolConfig.setMaxTotal(600);
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(1000);
        // 是否从池中取出连接前进行校验,如果校验失败,则取出连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(true);
        // 连接最小空闲时间
        jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);
        // 释放连接最大数目, 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(1024);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        return jedisPoolConfig;
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
