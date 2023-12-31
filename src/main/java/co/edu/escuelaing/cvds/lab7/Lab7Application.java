package co.edu.escuelaing.cvds.lab7;

import co.edu.escuelaing.cvds.lab7.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class Lab7Application {
	@Autowired
	ConfigurationService configurationService;

	public static void main(String[] args) {
		SpringApplication.run(Lab7Application.class, args);
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return (args) -> {

		};
	}

}
