package alankzh.blog.base.core.log;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MdcComponent {

    public void add(MdcKey key, String value) {
        MDC.put(key.name(), value);
    }

    public String get(MdcKey key) {
        return MDC.get(key.name());
    }

    public void clear() {
        Arrays.asList(MdcKey.values()).forEach(mdcKey -> {
            MDC.remove(mdcKey.name());
        });
    }
}