package shop.mtcoding.sporting_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import shop.mtcoding.sporting_server.core.config.JpaConfig;

@SpringBootApplication
public class SportingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportingServerApplication.class, args);
	}

}
