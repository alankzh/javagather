package alankzh.blog.base.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ComponentScan("alankzh.blog.base")
@EnableSwagger2
@PropertySource("classpath:base/rest/rest.properties")
@PropertySource(value = "file://${CONFIG_PATH}/base/rest/rest.properties", ignoreResourceNotFound = true)
public class BaseRestConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("alankzh.blog"))
                .paths(PathSelectors.any())
                .build();
    }
}
