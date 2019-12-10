package alankzh.blog.account.rest.config;

import alankzh.blog.account.core.AccountCoreConfig;
import alankzh.blog.base.rest.BaseRestConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("alankzh.blog.account.rest")
@Import({BaseRestConfig.class, AccountCoreConfig.class})
public class AccountServiceConfig {

}
