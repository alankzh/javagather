package alankzh.blog.redishotswitch.rest.config;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;


public class MagicManager {

    private volatile RedisConnectionFactory redisConnectionFactory;

    private RedisClusterConfiguration redisClusterConfiguration;

    private JedisPoolConfig jedisPoolConfig;

    public MagicManager(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig, String pwd){
        this.redisClusterConfiguration = redisClusterConfiguration;
        this.jedisPoolConfig = jedisPoolConfig;
        this.pwd = pwd;

        AAAJedisConnectionFactory conFactory = new AAAJedisConnectionFactory(redisClusterConfiguration);
        conFactory.setPoolConfig(jedisPoolConfig);
        conFactory.setPassword(pwd);
        conFactory.afterPropertiesSet();
        this.redisConnectionFactory = conFactory;

        runWithNoPwd = StringUtils.isEmpty(pwd);
    }

    private volatile boolean runWithNoPwd;
    private String pwd;

    // 模拟ops重新设置密码的方法
    public void setPassword(String password){
        this.pwd = password;
    }
    public String getPassword(){
        return this.pwd;
    }

    public boolean hotSwapMagic(RedisTemplate redisTemplate){
        // 当以非密码模式启动, 且此时密码不为空时(ops重新设置了密码), 进行切换.
        if (runWithNoPwd && !StringUtils.isEmpty(this.pwd)){
            refreshConnectionFactory(redisTemplate);
            return true;
        }
        return false;
    }

    private synchronized void refreshConnectionFactory(RedisTemplate redisTemplate){
        if (!runWithNoPwd){
            return;
        }
        AAAJedisConnectionFactory newConFactory = new AAAJedisConnectionFactory(redisClusterConfiguration);
        newConFactory.setPoolConfig(jedisPoolConfig);
        newConFactory.setPassword(pwd);
        newConFactory.afterPropertiesSet();
        this.redisConnectionFactory = newConFactory;
        redisTemplate.setConnectionFactory(newConFactory);
        runWithNoPwd = false;
    }

    public RedisConnectionFactory getRedisConnectionFactory(){
        return this.redisConnectionFactory;
    }

}
