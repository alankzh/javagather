package alankzh.blog.rediskeys.rest.config;

import alankzh.blog.base.rest.BaseRestConfig;
import alankzh.blog.rediskeys.core.config.RediskeysCoreConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("alankzh.blog.rediskeys.rest")
@Import({BaseRestConfig.class, RediskeysCoreConfig.class})
public class RediskeysServiceConfig {
}
