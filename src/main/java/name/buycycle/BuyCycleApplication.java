package name.buycycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class BuyCycleApplication {

	private static Logger logger = LoggerFactory.getLogger(BuyCycleApplication.class.getName());

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(name.buycycle.BuyCycleApplication.class);
		app.build().addListeners(new ApplicationPidFileWriter("./buycycle.pid"));
		app.run(args);
		logger.info("======================");
		logger.info("  Buycycle started... ");
		logger.info("======================");
	}
}
