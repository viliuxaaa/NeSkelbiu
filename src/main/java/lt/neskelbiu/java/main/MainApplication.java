package lt.neskelbiu.java.main;

import lt.neskelbiu.java.main.auth.AuthenticationService;
import lt.neskelbiu.java.main.auth.RegisterRequest;
import lt.neskelbiu.java.main.message.ResponseMessage;
import lt.neskelbiu.java.main.poster.*;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.posterImg.PosterImgRepository;
import lt.neskelbiu.java.main.posterImg.PosterImgService;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserRepository;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.userImg.UserImg;
import lt.neskelbiu.java.main.userImg.UserImgController;
import lt.neskelbiu.java.main.userImg.UserImgRepository;
import lt.neskelbiu.java.main.userImg.UserImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
			UserService userService,
			PosterImgService posterImgService,
			UserImgService userImgService,
			UserImgController userImgController
	) {
		return args -> {

//			var admin1 = RegisterRequest.builder()
//					.username("admin1")
//					.password("admin1")
//					.firstName("Admin1")
//					.lastName("Admin1")
//					.email("admin1@gmail.com")
//					.build();
//			System.out.println("Admin1 token:" + service.register(admin1, ADMIN).getAccessToken());
//
//			var admin2 = RegisterRequest.builder()
//					.username("admin2")
//					.password("admin2")
//					.firstName("Admin2")
//					.lastName("Admin2")
//					.email("admin2@gmail.com")
//					.build();
//			System.out.println("Admin2 token:" + service.register(admin2, ADMIN).getAccessToken());
//
//			var manager1 = RegisterRequest.builder()
//					.username("manager1")
//					.password("manager1")
//					.firstName("Manager1")
//					.lastName("Manager1")
//					.email("manager1@gmail.com")
//					.build();
//			System.out.println("Manager1 token:" + service.register(manager1, MANAGER).getAccessToken());
//
//			var manager2 = RegisterRequest.builder()
//					.username("manager2")
//					.password("manager2")
//					.firstName("Manager2")
//					.lastName("Manager2")
//					.email("manager2@gmail.com")
//					.build();
//			System.out.println("Manager2 token:" + service.register(manager2, MANAGER).getAccessToken());
//
//			var user1 = RegisterRequest.builder()
//					.username("user1")
//					.password("user1")
//					.firstName("User1")
//					.lastName("User1")
//					.email("user1@gmail.com")
//					.build();
//			System.out.println("User1 token:" + service.register(user1, USER).getAccessToken());
//
//			var user2 = RegisterRequest.builder()
//					.username("user2")
//					.password("user2")
//					.firstName("User2")
//					.lastName("User2")
//					.email("user2@gmail.com")
//					.build();
//			System.out.println("User2 token:" + service.register(user2, USER).getAccessToken());
//
//			var poster1 = Poster.builder()
//					.postName("Labai nuostabus puodelis, turim tokiu daug")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
//					.description("Labai nuostabus puodelis, turim tokiu daug, pirkite puodelis, myliu puodelius, labai ilgas tekstas, asasads</br> asasasasas</br> sitai turi padaryti tave laiminga")
//					.status(Status.ACTIVE)
//					.user(userService.findByUsername("admin1"))
//					.city(City.VILNIUS)
//					.website("WEBSITE")
//					.videoLink("https://www.youtube.com/watch?v=cmSbXsFE3l8&ab_channel=AnnaKendrickVEVO")
//					.phoneNumber("37061111111")
//					.createdAt(LocalDateTime.of(2023,5,1,2,1,1,1))
//					.updatedAt(null)
//					.price(1L)
//					.build();
//			posterService.save(poster1);
//			File file1 = ResourceUtils.getFile("classpath:static/cup/default1.png");
//			File file2 = ResourceUtils.getFile("classpath:static/cup/default2.png");
//			File file3 = ResourceUtils.getFile("classpath:static/cup/default3.png");
//			File file4 = ResourceUtils.getFile("classpath:static/cup/default4.png");
//			File file5 = ResourceUtils.getFile("classpath:static/cup/default5.png");
//			File file6 = ResourceUtils.getFile("classpath:static/cup/default6.png");
//			InputStream inputStream1 = new FileInputStream(file1);
//			InputStream inputStream2 = new FileInputStream(file2);
//			InputStream inputStream3 = new FileInputStream(file3);
//			InputStream inputStream4 = new FileInputStream(file4);
//			InputStream inputStream5 = new FileInputStream(file5);
//			InputStream inputStream6 = new FileInputStream(file6);
//			byte[] bytes1 = inputStream1.readAllBytes();
//			byte[] bytes2 = inputStream2.readAllBytes();
//			byte[] bytes3 = inputStream3.readAllBytes();
//			byte[] bytes4 = inputStream4.readAllBytes();
//			byte[] bytes5 = inputStream5.readAllBytes();
//			byte[] bytes6 = inputStream6.readAllBytes();
//			MultipartFile multipartFile1 = new ManualMultipartFile(bytes1, "default1.png", "image/png");
//			MultipartFile multipartFile2 = new ManualMultipartFile(bytes2, "default2.png", "image/png");
//			MultipartFile multipartFile3 = new ManualMultipartFile(bytes3, "default3.png", "image/png");
//			MultipartFile multipartFile4 = new ManualMultipartFile(bytes4, "default4.png", "image/png");
//			MultipartFile multipartFile5 = new ManualMultipartFile(bytes5, "default5.png", "image/png");
//			MultipartFile multipartFile6 = new ManualMultipartFile(bytes6, "default6.png", "image/png");
//			Poster poster11 = posterService.findById(1L);
//			List<MultipartFile> list1 = Arrays.asList(multipartFile1, multipartFile2, multipartFile3, multipartFile4, multipartFile5, multipartFile6);
//			posterImgService.store(list1, poster11);
//
//			var poster2 = Poster.builder()
//					.postName("Aukštos labas Kokybės Bluetooth Ausinės su Triukšmo Slopinimu")
//					.categoryA(CategoryA.PRIEDAI_AKSESUARAI)
//					.categoryB(CategoryB.BELAIDZIO_TINKLO_IRANGA)
//					.description("Mėgaukitės puikia garso kokybe ir išskirtiniu komfortu su mūsų aukštos kokybės Bluetooth ausinėmis su triukšmo slopinimu. Šios ausinės ne tik suteiks jums puikų garso potyrių, bet ir padės jums įsiklausyti į muziką, skambučius arba podcastus be aplinkinių trikdžių.<br />\n" +
//							"<br />\n" +
//							"Savybės:</br>\n" +
//							"<br />\n" +
//							"Triukšmo slopinimas: Patirtis visišką susikaupimą ir muzikos pasaulio pamiršimą su išskirtiniu triukšmo slopinimu. Nepriklausomai nuo to, ar esate perpildytame biure, lėktuve ar viešame transporte, joks triukšmas jūsų nesutrikdys.<br/>\n" +
//							"Bluetooth 5.0: Lengvai jungiantis prie jūsų įrenginio, šios ausinės palaiko Bluetooth 5.0 technologiją, užtikrinančią stabilų ryšį ir aukštą garso kokybę.<br />\n" +
//							"Ilga Baterijos Darbo Laikas: Mėgaukitės iki 20 valandų nepertraukiamu muzikos klausymu su vienu įkrovimu. Tai puikiai tinka ilgiems kelionėms ar sportui.<br />\n" +
//							"Patogumas Visą Dieną: Ergonominis dizainas ir minkšti pagalvėlės užtikrins jūsų patogumą net ilgiausiems klausymosi seansams.<br />\n" +
//							"Valdymas Lengvai Pasiekiamas: Integruoti valdymo mygtukai leis lengvai valdyti muziką, skambučius ir garsų stiprumą.")
//					.status(Status.NOTACTIVE)
//					.user(userService.findByUsername("admin2"))
//					.city(City.KLAIPEDA)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37062222222")
//					.createdAt(LocalDateTime.of(2023,4,7,1,1,1,1))
//					.updatedAt(null)
//					.price(2L)
//					.build();
//			posterService.save(poster2);
//			File file7 = ResourceUtils.getFile("classpath:static/headset/default1.png");
//			File file8 = ResourceUtils.getFile("classpath:static/headset/default2.png");
//			File file9 = ResourceUtils.getFile("classpath:static/headset/default3.png");
//			File file10 = ResourceUtils.getFile("classpath:static/headset/default4.png");
//			File file11 = ResourceUtils.getFile("classpath:static/headset/default5.png");
//			File file12 = ResourceUtils.getFile("classpath:static/headset/default6.png");
//			InputStream inputStream7 = new FileInputStream(file7);
//			InputStream inputStream8 = new FileInputStream(file8);
//			InputStream inputStream9 = new FileInputStream(file9);
//			InputStream inputStream10 = new FileInputStream(file10);
//			InputStream inputStream11 = new FileInputStream(file11);
//			InputStream inputStream12 = new FileInputStream(file12);
//			byte[] bytes7 = inputStream7.readAllBytes();
//			byte[] bytes8 = inputStream8.readAllBytes();
//			byte[] bytes9 = inputStream9.readAllBytes();
//			byte[] bytes10 = inputStream10.readAllBytes();
//			byte[] bytes11 = inputStream11.readAllBytes();
//			byte[] bytes12 = inputStream12.readAllBytes();
//			MultipartFile multipartFile7 = new ManualMultipartFile(bytes7, "default1.png", "image/png");
//			MultipartFile multipartFile8 = new ManualMultipartFile(bytes8, "default2.png", "image/png");
//			MultipartFile multipartFile9 = new ManualMultipartFile(bytes9, "default3.png", "image/png");
//			MultipartFile multipartFile10 = new ManualMultipartFile(bytes10, "default4.png", "image/png");
//			MultipartFile multipartFile11 = new ManualMultipartFile(bytes11, "default5.png", "image/png");
//			MultipartFile multipartFile12 = new ManualMultipartFile(bytes12, "default6.png", "image/png");
//			Poster poster22 = posterService.findById(2L);
//			List<MultipartFile> list2 = Arrays.asList(multipartFile7, multipartFile8, multipartFile9, multipartFile10, multipartFile11, multipartFile12);
//			posterImgService.store(list2, poster22);
//
//			var poster3 = Poster.builder()
//					.postName("Aukštos Našumo Nešiojamas Kompiuteris su 10-osios Kartos Intel procesoriumi")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.NESIOJAMI_KOMPIUTERIAI)
//					.description("Siekiant labas patenkinti jūsų darbo ir pramogų poreikius, pristatome aukštos našumo nešiojamą kompiuterį su 10-osios kartos Intel procesoriumi. Šis kompiuteris suteiks jums galimybę efektyviai dirbti, kurti, žaisti žaidimus ir daugiau, nepriklausomai nuo to, kur esate.")
//					.status(Status.RESERVED)
//					.user(userService.findByUsername("manager1"))
//					.city(City.KAUNAS)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37063333333")
//					.createdAt(LocalDateTime.of(2023,3,1,6,1,1,1))
//					.updatedAt(null)
//					.price(3L)
//					.build();
//			posterService.save(poster3);
//			File file13 = ResourceUtils.getFile("classpath:static/laptop/default1.png");
//			File file14 = ResourceUtils.getFile("classpath:static/laptop/default2.png");
//			File file15 = ResourceUtils.getFile("classpath:static/laptop/default3.png");
//			InputStream inputStream13 = new FileInputStream(file13);
//			InputStream inputStream14 = new FileInputStream(file14);
//			InputStream inputStream15 = new FileInputStream(file15);
//			byte[] bytes13 = inputStream13.readAllBytes();
//			byte[] bytes14 = inputStream14.readAllBytes();
//			byte[] bytes15 = inputStream15.readAllBytes();
//			MultipartFile multipartFile13 = new ManualMultipartFile(bytes13, "default1.png", "image/png");
//			MultipartFile multipartFile14 = new ManualMultipartFile(bytes14, "default2.png", "image/png");
//			MultipartFile multipartFile15 = new ManualMultipartFile(bytes15, "default3.png", "image/png");
//			Poster poster33 = posterService.findById(3L);
//			List<MultipartFile> list3 = Arrays.asList(multipartFile13, multipartFile14, multipartFile15);
//			posterImgService.store(list3, poster33);
//
//			var poster4 = Poster.builder()
//					.postName("PlayStation Žaidimų Konsolė su Naujausia Technologija ir Išskirtiniais Žaidimais")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
//					.description("Prisistatome PlayStation žaidimų konsolę, kurios galingumas ir aukštos klasės technologija leis jums patirti įspūdingą žaidimų pasaulį. Ši konsolė ne tik siūlo naujausius žaidimus, bet ir suteikia unikalų žaidimų ir pramogų patyrimą.")
//					.status(Status.ACTIVE)
//					.user(userService.findByUsername("manager2"))
//					.city(City.ALYTUS)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37064444444")
//					.createdAt(LocalDateTime.of(2024,1,1,5,1,1,1))
//					.updatedAt(null)
//					.price(4L)
//					.build();
//			posterService.save(poster4);
//			File file16 = ResourceUtils.getFile("classpath:static/playstation/default1.png");
//			InputStream inputStream16 = new FileInputStream(file16);
//			byte[] bytes16 = inputStream16.readAllBytes();
//			MultipartFile multipartFile16 = new ManualMultipartFile(bytes16, "default1.png", "image/png");
//			Poster poster44 = posterService.findById(4L);
//			List<MultipartFile> list4 = Arrays.asList(multipartFile16);
//			posterImgService.store(list4, poster44);
//
//			var poster5 = Poster.builder()
//					.postName("Xbox Žaidimų Konsolė su Aukštos Klasės Grafika ir Išskirtiniais Žaidimais")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
//					.description("Mėgaukitės puikia žaidimų patirtimi su mūsų Xbox žaidimų konsolės pagalba. Ši konsolė sujungia aukščiausios kokybės grafiką su išskirtiniais žaidimais ir praturtina jūsų žaidimų pasaulį visiškai naujomis galimybėmis.")
//					.status(Status.RESERVED)
//					.user(userService.findByUsername("user1"))
//					.city(City.JONAVA)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37065555555")
//					.createdAt(LocalDateTime.of(2023,1,1,1,4,1,1))
//					.updatedAt(LocalDateTime.of(2024,1,1,1,1,1,1))
//					.price(5L)
//					.build();
//			posterService.save(poster5);
//			File file17 = ResourceUtils.getFile("classpath:static/xbox/default1.png");
//			InputStream inputStream17 = new FileInputStream(file17);
//			byte[] bytes17 = inputStream17.readAllBytes();
//			MultipartFile multipartFile17 = new ManualMultipartFile(bytes17, "default1.png", "image/png");
//			Poster poster55 = posterService.findById(5L);
//			List<MultipartFile> list5 = Arrays.asList(multipartFile17);
//			posterImgService.store(list5, poster55);
//
//			var poster6 = Poster.builder()
//					.postName("Ergonominė Kompiuterinė Pele su Daugiafunkcėmis Galimybėmis")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
//					.description("Pristatome ergonominę kompiuterinę pelę, sukuriamą siekiant užtikrinti komfortą ir efektyvumą ilgą laiką dirbant prie kompiuterio. Ši pele pasižymi patogiu dizainu ir gausiu funkcijų spektru, leidžiančiu jums veikti tiksliai ir efektyviai.")
//					.status(Status.ACTIVE)
//					.user(userService.findByUsername("user2"))
//					.city(City.KAUNAS)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37066666666")
//					.createdAt(LocalDateTime.of(2023,2,1,1,3,1,1))
//					.updatedAt(null)
//					.price(6L)
//					.build();
//			posterService.save(poster6);
//			File file18 = ResourceUtils.getFile("classpath:static/mouse/default1.png");
//			InputStream inputStream18 = new FileInputStream(file18);
//			byte[] bytes18 = inputStream18.readAllBytes();
//			MultipartFile multipartFile18 = new ManualMultipartFile(bytes18, "default1.png", "image/png");
//			Poster poster66 = posterService.findById(6L);
//			List<MultipartFile> list6 = Arrays.asList(multipartFile18);
//			posterImgService.store(list6, poster66);
//
//			var poster7 = Poster.builder()
//					.postName("Mechaninė Klaviatūra su RGB Apšvietimu ir Naudotojo Prisitaikymo Galimybėmis")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
//					.description("Pristatome aukštos kokybės mechaninę klaviatūrą su įspūdingu RGB apšvietimu, suteikiančiu ne tik funkcinį, bet ir vizualinį privalumą. Ši klaviatūra puikiai tinka tiek profesionaliam darbui, tiek žaidimams, ir leidžia jums prisitaikyti pagal savo poreikius.")
//					.status(Status.ACTIVE)
//					.user(userService.findByUsername("user2"))
//					.city(City.KAUNAS)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37066666666")
//					.createdAt(LocalDateTime.of(2023,2,1,1,1,2,1))
//					.updatedAt(null)
//					.price(6L)
//					.build();
//			posterService.save(poster7);
//			File file19 = ResourceUtils.getFile("classpath:static/keyboard/default1.png");
//			InputStream inputStream19 = new FileInputStream(file19);
//			byte[] bytes19 = inputStream19.readAllBytes();
//			MultipartFile multipartFile19 = new ManualMultipartFile(bytes19, "default1.png", "image/png");
//			Poster poster77 = posterService.findById(7L);
//			List<MultipartFile> list7 = Arrays.asList(multipartFile19);
//			posterImgService.store(list7, poster77);
//
//			var poster8 = Poster.builder()
//					.postName("Flagship Išmanusis Telefonas su Naujausia Technologija")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
//					.description("Prisistatome aukščiausios klasės išmanųjį telefoną, kuris derina puikų dizainą su naujausia technologija. Šis telefono modelis leis jums patirti aukščiausią našumą, fotografavimo galimybes ir ryšio stabilumą, užtikrinant, kad jūsų išmaniojo telefono patirtis būtų nepakartojama.")
//					.status(Status.ACTIVE)
//					.user(userService.findByUsername("user2"))
//					.city(City.KAUNAS)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37066666666")
//					.createdAt(LocalDateTime.of(2023,2,1,1,1,1,18))
//					.updatedAt(null)
//					.price(6L)
//					.build();
//			posterService.save(poster8);
//			File file20 = ResourceUtils.getFile("classpath:static/phone/default1.png");
//			InputStream inputStream20 = new FileInputStream(file20);
//			byte[] bytes20 = inputStream20.readAllBytes();
//			MultipartFile multipartFile20 = new ManualMultipartFile(bytes20, "default1.png", "image/png");
//			Poster poster88 = posterService.findById(8L);
//			List<MultipartFile> list8 = Arrays.asList(multipartFile20);
//			posterImgService.store(list8, poster88);
//
//			var poster9 = Poster.builder()
//					.postName("Aukštos Raiškos 4K Televizorius su Išmaniąja Technologija")
//					.categoryA(CategoryA.KOMPIUTERIAI)
//					.categoryB(CategoryB.KOMPIUTERIAI_KITA)
//					.description("Pasitvirtinus aukščiausios kokybės vaizdo ir garsą, pristatome 4K televizorių su išmaniąja technologija, leidžiančia jums mėgautis kino teatro patirtimi tiesiog namuose. Šis televizorius išpildys jūsų norus dėl puikios vaizdo raiškos ir įspūdingos garso kokybės.")
//					.status(Status.ACTIVE)
//					.user(userService.findByUsername("user2"))
//					.city(City.KAUNAS)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37069999999")
//					.createdAt(LocalDateTime.of(2023,2,1,1,1,1,19))
//					.updatedAt(null)
//					.price(6L)
//					.build();
//			posterService.save(poster9);
//			File file21 = ResourceUtils.getFile("classpath:static/tv/default1.png");
//			InputStream inputStream21 = new FileInputStream(file21);
//			byte[] bytes21 = inputStream21.readAllBytes();
//			MultipartFile multipartFile21 = new ManualMultipartFile(bytes21, "default1.png", "image/png");
//			Poster poster99 = posterService.findById(9L);
//			List<MultipartFile> list9 = Arrays.asList(multipartFile21);
//			posterImgService.store(list9, poster99);
//
//			var poster10 = Poster.builder()
//					.postName("Klasės USB Mikrofonas su Kristaliniu Garso Kokybe")
//					.categoryA(CategoryA.ISORINIAI_IRENGINIAI)
//					.categoryB(CategoryB.ISORINIAI_IRENGINIAI_KITA)
//					.description("Pristatome aukštos kokybės studijinio klasės USB mikrofoną, kuris suteiks jūsų garso įrašams profesionalią ir kristalinią garso kokybę. Nesvarbu, ar jūs kuriate turinį, transliuojate tiesiogiai, ar įrašote muziką, šis mikrofonas leis jums pasiekti aukščiausią garso standartą.")
//					.status(Status.ACTIVE)
//					.user(userService.findByUsername("user2"))
//					.city(City.KAUNAS)
//					.website("WEBSITE")
//					.videoLink("VIDEO")
//					.phoneNumber("37069999999")
//					.createdAt(LocalDateTime.of(2023,2,1,1,1,1,10))
//					.updatedAt(null)
//					.price(6L)
//					.build();
//			posterService.save(poster10);
//			File file22 = ResourceUtils.getFile("classpath:static/microphone/default1.png");
//			InputStream inputStream22 = new FileInputStream(file22);
//			byte[] bytes22 = inputStream22.readAllBytes();
//			MultipartFile multipartFile22 = new ManualMultipartFile(bytes22, "default1.png", "image/png");
//			Poster poster110 = posterService.findById(10L);
//			List<MultipartFile> list10 = Arrays.asList(multipartFile22);
//			posterImgService.store(list10, poster110);
		};
	}

}
