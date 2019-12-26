package alankzh.blog.comment.rest;

import alankzh.blog.account.api.AccountApiConstant;
import alankzh.blog.base.rest.BaseRestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients(basePackages = {AccountApiConstant.ACCOUNT_API_PACKAGES})
@Import({BaseRestConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run();
    }

}
