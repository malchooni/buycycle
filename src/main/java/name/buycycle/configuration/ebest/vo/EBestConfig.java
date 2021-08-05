package name.buycycle.configuration.ebest.vo;

import name.buycycle.vendor.ebest.config.vo.Connect;
import name.buycycle.vendor.ebest.config.vo.User;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

/**
 * ebest config value object
 */
@Configuration
@ConfigurationProperties(prefix = "ebest")
@ConstructorBinding
public class EBestConfig {
    private String resRootPath;
    private Connect connect;
    private User user;

    public String getResRootPath() {
        return resRootPath;
    }

    public void setResRootPath(String resRootPath) {
        this.resRootPath = resRootPath;
    }

    public Connect getConnect() {
        return connect;
    }

    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}