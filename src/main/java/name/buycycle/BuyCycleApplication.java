package name.buycycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * welcome to buycycle https://buycycle.name
 *
 * @author : ijyoon
 * @date : 2021/03/24
 */
@EnableEurekaClient
@SpringBootApplication
public class BuyCycleApplication {

  private static Logger logger = LoggerFactory.getLogger(BuyCycleApplication.class.getName());

  public static void main(String[] args) {
    SpringApplicationBuilder app = new SpringApplicationBuilder(
        name.buycycle.BuyCycleApplication.class);
    app.build().addListeners(new ApplicationPidFileWriter("./buycycle.pid"));
    app.run(args);
    if (logger.isInfoEnabled()) {
      logger.info("======================");
      logger.info("  Buycycle started... ");
      logger.info("======================");
    }
  }
}
