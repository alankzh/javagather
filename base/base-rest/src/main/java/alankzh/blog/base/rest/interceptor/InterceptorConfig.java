package alankzh.blog.base.rest.interceptor;

import alankzh.blog.base.rest.MdcComponent;
import alankzh.blog.base.rest.MdcKey;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    AccessLogInterceptor accessLogInterceptor;

    @Autowired
    MdcComponent mdcComponent;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLogInterceptor).addPathPatterns("/**");
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        registry.addInterceptor(localeChangeInterceptor);
    }


    // feign 做header转发.
    @Bean
    public RequestInterceptor headerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        requestTemplate.header(name, values);
                    }
                }
                String logTrace = request.getHeader("x-trace");
                if (StringUtils.isEmpty(logTrace)) {
                    requestTemplate.header("x-trace", mdcComponent.get(MdcKey.logger_trace));
                }
            }
        };
    }
}
