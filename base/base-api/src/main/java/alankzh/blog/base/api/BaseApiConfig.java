package alankzh.blog.base.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:base/api/api.properties")
@PropertySource(value = "file://${CONFIG_PATH}/base/api/api.properties", ignoreResourceNotFound = true)
public class BaseApiConfig {
}
