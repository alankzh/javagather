package alankzh.blog.rediskeys.core.config;

import alankzh.blog.base.core.config.DataSourceConfig;
import alankzh.blog.base.core.mybatisplus.MybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan("alankzh.blog.rediskeys.core")
@MapperScan({"alankzh.blog.rediskeys.core"})
@PropertySource("classpath:rediskeys/core/rediskeys-core.properties")
// 仅为了测试类导入使用,实际工程测试类会有自己的config
@Import({DataSourceConfig.class, MybatisPlusConfig.class})
public class RediskeysCoreConfig {

}
