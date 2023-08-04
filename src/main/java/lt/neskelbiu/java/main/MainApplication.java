package lt.neskelbiu.java.main;

import lt.neskelbiu.java.main.auth.AuthenticationService;
import lt.neskelbiu.java.main.auth.RegisterRequest;
import lt.neskelbiu.java.main.poster.City;
import lt.neskelbiu.java.main.poster.Poster;
import lt.neskelbiu.java.main.poster.PosterService;
import lt.neskelbiu.java.main.poster.Status;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserRepository;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.userImg.UserImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;

import static lt.neskelbiu.java.main.user.Role.*;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service,
			PosterService posterService,
			UserService userService
	) {
		return args -> {
			var admin1 = RegisterRequest.builder()
					.username("admin1")
					.password("admin1")
					.firstName("Admin1")
					.lastName("Admin1")
					.email("admin1@gmail.com")

					.build();
			System.out.println("Admin1 token:" + service.register(admin1, ADMIN).getAccessToken());

			var admin2 = RegisterRequest.builder()
					.username("admin2")
					.password("admin2")
					.firstName("Admin2")
					.lastName("Admin2")
					.email("admin2@gmail.com")
					.build();
			System.out.println("Admin2 token:" + service.register(admin2, ADMIN).getAccessToken());

			var manager1 = RegisterRequest.builder()
					.username("manager1")
					.password("manager1")
					.firstName("Manager1")
					.lastName("Manager1")
					.email("manager1@gmail.com")
					.build();
			System.out.println("Manager1 token:" + service.register(manager1, MANAGER).getAccessToken());

			var manager2 = RegisterRequest.builder()
					.username("manager2")
					.password("manager2")
					.firstName("Manager2")
					.lastName("Manager2")
					.email("manager2@gmail.com")
					.build();
			System.out.println("Manager2 token:" + service.register(manager2, MANAGER).getAccessToken());

			var user1 = RegisterRequest.builder()
					.username("user1")
					.password("user1")
					.firstName("User1")
					.lastName("User1")
					.email("user1@gmail.com")
					.build();
			System.out.println("User1 token:" + service.register(user1, USER).getAccessToken());

			var user2 = RegisterRequest.builder()
					.username("user2")
					.password("user2")
					.firstName("User2")
					.lastName("User2")
					.email("user2@gmail.com")
					.build();
			System.out.println("User2 token:" + service.register(user2, USER).getAccessToken());

			var poster1 = Poster.builder()
					.postName("Posters1")
					.categoryA(CategoryA.KOMPIUTERIAI)
					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
					.description("Description1")
					.status(Status.ACTIVE)
					.user(userService.findByUsername("admin1"))
					.city(City.ALYTUS)
					.website("WEBSITE")
					.videoLink("VIDEO")
					.phoneNumber("37061111111")
					.createdAt(LocalDateTime.of(2023,5,1,1,1,1,1))
					.updatedAt(null)
					.price(1L)
					.build();
			posterService.save(poster1);

			var poster2 = Poster.builder()
					.postName("Posters2")
					.categoryA(CategoryA.KOMPIUTERIAI)
					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
					.description("Description2")
					.status(Status.NOTACTIVE)
					.user(userService.findByUsername("admin2"))
					.city(City.KLAIPEDA)
					.website("WEBSITE")
					.videoLink("VIDEO")
					.phoneNumber("37062222222")
					.createdAt(LocalDateTime.of(2023,4,1,1,1,1,1))
					.updatedAt(null)
					.price(2L)
					.build();
			posterService.save(poster2);

			var poster3 = Poster.builder()
					.postName("Posters3")
					.categoryA(CategoryA.KOMPIUTERIAI)
					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
					.description("Description1")
					.status(Status.RESERVED)
					.user(userService.findByUsername("manager1"))
					.city(City.KAUNAS)
					.website("WEBSITE")
					.videoLink("VIDEO")
					.phoneNumber("37063333333")
					.createdAt(LocalDateTime.of(2023,3,1,1,1,1,1))
					.updatedAt(null)
					.price(3L)
					.build();
			posterService.save(poster3);

			var poster4 = Poster.builder()
					.postName("Posters4")
					.categoryA(CategoryA.KOMPIUTERIAI)
					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
					.description("Description4")
					.status(Status.ACTIVE)
					.user(userService.findByUsername("manager2"))
					.city(City.ALYTUS)
					.website("WEBSITE")
					.videoLink("VIDEO")
					.phoneNumber("37064444444")
					.createdAt(LocalDateTime.of(2024,1,1,1,1,1,1))
					.updatedAt(null)
					.price(4L)
					.build();
			posterService.save(poster4);

			var poster5 = Poster.builder()
					.postName("Posters5")
					.categoryA(CategoryA.KOMPIUTERIAI)
					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
					.description("Description1")
					.status(Status.RESERVED)
					.user(userService.findByUsername("user1"))
					.city(City.JONAVA)
					.website("WEBSITE")
					.videoLink("VIDEO")
					.phoneNumber("37065555555")
					.createdAt(LocalDateTime.of(2023,1,1,1,1,1,1))
					.updatedAt(LocalDateTime.of(2024,1,1,1,1,1,1))
					.price(5L)
					.build();
			posterService.save(poster5);

			var poster6 = Poster.builder()
					.postName("Posters6")
					.categoryA(CategoryA.KOMPIUTERIAI)
					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
					.description("Description1")
					.status(Status.ACTIVE)
					.user(userService.findByUsername("user2"))
					.city(City.KAUNAS)
					.website("WEBSITE")
					.videoLink("VIDEO")
					.phoneNumber("37066666666")
					.createdAt(LocalDateTime.of(2023,2,1,1,1,1,1))
					.updatedAt(null)
					.price(6L)
					.build();
			posterService.save(poster6);
		};
	}

}
