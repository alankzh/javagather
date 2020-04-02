package alankzh.blog.redishotswitch.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class JedisConfig {



    @SuppressWarnings("unchecked")
    @Bean
    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


//    /**
//     * 支持版connectionFactory
//     */
//    @Bean
//    public AAAJedisConnectionFactory aaaJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,
//                                                         JedisPoolConfig jedisPoolConfig){
//        AAAJedisConnectionFactory jedisConnectionFactory = new AAAJedisConnectionFactory(redisClusterConfiguration);
//        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//        jedisConnectionFactory.setTimeout(20000);
//
////        jedisConnectionFactory.setPassword("123456");
//        return jedisConnectionFactory;
//    }

    /**
     * 原始的Connectionfactory
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,
                                                         JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setTimeout(20000);

//        jedisConnectionFactory.setPassword("123456");

        return jedisConnectionFactory;
    }


    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(6);

        Set<RedisNode> redisNodes = new HashSet<RedisNode>();
        String hostIp = "172.17.0.1";
        int initPort = 8001;
        for (int i=0; i<6; i++){
            RedisNode redisNode = new RedisNode(hostIp, initPort + i);
            redisNodes.add(redisNode);
        }
        redisClusterConfiguration.setClusterNodes(redisNodes);
        return redisClusterConfiguration;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲
        jedisPoolConfig.setMaxIdle(100);
        // 最大链接
        jedisPoolConfig.setMaxTotal(100);
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

}
