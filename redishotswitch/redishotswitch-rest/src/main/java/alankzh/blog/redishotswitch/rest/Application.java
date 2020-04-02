package alankzh.blog.redishotswitch.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run();

//        boolean getTestOnBorrow = false;
//        boolean create = true;
//        boolean testOnCreate = true;
//        System.out.println(getTestOnBorrow || create && testOnCreate);
//        System.out.println(getTestOnBorrow || (create && testOnCreate));
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("alankzh.blog.redishotswitch.rest"))
                .paths(PathSelectors.any())
                .build();
    }

}
