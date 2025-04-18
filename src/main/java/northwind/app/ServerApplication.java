package northwind.app;

import northwind.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({AppConfig.class})
public class ServerApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	return application.sources(ServerApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		System.err.println("##########ServerApplication#######");
		
	}

}
