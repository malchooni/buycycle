package name.buycycle.vendor.ebest.config;

import name.buycycle.vendor.ebest.handler.XASessionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ebest 서버 연결 확인
 */
@Configuration
public class XASessionConfigure implements WebMvcConfigurer {

    @Autowired
    private XASessionChecker xaSessionChecker;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(xaSessionChecker)
                .addPathPatterns("/data/ebest/real", "/data/ebest/query");
    }
}
