package lt.neskelbiu.java.main;

import lt.neskelbiu.java.main.auth.AuthenticationService;
import lt.neskelbiu.java.main.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

import static lt.neskelbiu.java.main.user.Role.*;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.username("admin")
					.password("admin")
					.role(ADMIN)
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@gmail.com")
					.build();
			System.out.println("Admin token:" + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.username("manager")
					.password("manager")
					.role(MANAGER)
					.firstname("Manager")
					.lastname("Manager")
					.email("manager@gmail.com")
					.build();
			System.out.println("Manager token:" + service.register(manager).getAccessToken());

			var user = RegisterRequest.builder()
					.username("user")
					.password("user")
					.role(USER)
					.firstname("User1")
					.lastname("User2")
					.email("user@gmail.com")
					.build();
			System.out.println("User token:" + service.register(user).getAccessToken());
		};
	}

}
