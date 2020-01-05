package alankzh.blog.base.rest.interceptor;

import alankzh.blog.base.core.log.MdcComponent;
import alankzh.blog.base.core.log.MdcKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
@Slf4j
public class AccessLogInterceptor extends HandlerInterceptorAdapter {
    @Value("${access.log.enabled}")
    private Boolean accessLogEnabled;

    @Autowired
    MdcComponent mdcComponent;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String logTrace = request.getHeader("x-trace");
        if (StringUtils.isEmpty(logTrace)) {
            logTrace = UUID.randomUUID().toString();
        }
        mdcComponent.add(MdcKey.logger_trace, logTrace);

        request.setAttribute("x-time-start", System.currentTimeMillis());
        if (accessLogEnabled) {
            log.info(buildAccessLogRequest(request));
        } else {
            log.debug(buildAccessLogRequest(request));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (accessLogEnabled) {
            log.info(buildAccessLogResponse(request, response));
        } else {
            log.debug(buildAccessLogResponse(request, response));
        }
        mdcComponent.clear();
    }

    /**
     * REMOTE_IP FROWARD_FOR METHOD ACCESS_URL?QUERY_STRING USER_AGENT
     */
    private String buildAccessLogRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(handleString(request.getRemoteAddr())).append(" ");
        sb.append(handleString(request.getHeader("X-Forwarded-For"))).append(" ");
        sb.append(handleString(request.getMethod())).append(" ");
        sb.append(handleString(request.getRequestURI())).append(" ");
        sb.append(handleString(request.getQueryString())).append(" ");
        sb.append(handleString(request.getHeader("User-Agent")));
        return sb.toString();
    }

    private String handleString(String message) {
        return StringUtils.isEmpty(message) ? " -" : message;
    }

    /**
     * RESPONSE_STATUS TIME_SPEND
     */
    private String buildAccessLogResponse(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        sb.append(response.getStatus()).append(" ");
        sb.append((System.currentTimeMillis() - (Long) request.getAttribute("x-time-start"))).append("ms");
        return sb.toString();
    }
}
