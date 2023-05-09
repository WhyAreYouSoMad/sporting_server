package shop.mtcoding.sporting_server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportingServerApplication.class, args);
	}

	// @Profile("dev")
	// @Bean
	// public CommandLineRunner initData1() {
	// return (args) -> {
	// System.out.println("Dev CommandLineRunner 실행됨
	// ------------------------------------------");
	// };
	// }

	// // 스프링서버가 최초 실행될 때 단 한번 실행되는 배치 메서드이다. prod 모드일때만 작동한다.
	// @Profile("prod")
	// @Bean
	// public CommandLineRunner initData3() {
	// return (args) -> {
	// System.out.println("Prod CommandLineRunner 실행됨
	// ------------------------------------------");
	// };
	// }
}
