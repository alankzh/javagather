package alankzh.blog.account.core;

import alankzh.blog.base.core.config.DataSourceConfig;
import alankzh.blog.base.core.mybatis.MybatisConfig;
import alankzh.blog.base.core.mybatisplus.MybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;

@Configuration
@ComponentScan("alankzh.blog.account.core")
@MapperScan({"alankzh.blog.account.core.mybatisplus", "alankzh.blog.account.core.mybatis"})

// jpa相关配置
//@EnableJpaRepositories(basePackages = "alankzh.blog.account.core.jpa")
//@EntityScan(basePackages = "alankzh.blog.account.core.jpa")
@Import({DataSourceConfig.class, MybatisPlusConfig.class})
public class AccountCoreConfig {

}
