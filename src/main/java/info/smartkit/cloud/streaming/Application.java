package info.smartkit.cloud.streaming;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


//@PropertySources({ @PropertySource(value = "classpath:application-${spring.profiles.active}.properties") })
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class Application {
	
	private static Logger LOG = LogManager.getLogger(Application.class);
	//
	private static Class<Application> applicationClass = Application.class;

	//
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		//
		return application.sources(applicationClass);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}