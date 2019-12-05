package alankzh.blog.base.rest.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Component
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    AccessLogInterceptor accessLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLogInterceptor).addPathPatterns("/**");
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        registry.addInterceptor(localeChangeInterceptor);
    }
}
