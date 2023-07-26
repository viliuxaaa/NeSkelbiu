package lt.neskelbiu.java.main.poster;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.user.UserRepository;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.userImg.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lt.neskelbiu.java.main.poster.Poster;
import lt.neskelbiu.java.main.poster.PosterService;

@RestController
@RequestMapping("/api/v1/posters") // Base URL mapping for all methods in this controller
@RequiredArgsConstructor
public class PosterController {

    final private PosterService posterService;
    final private UserService userService;

    @GetMapping("/get")
    public ResponseEntity<List<Poster>> getAllPosters() {

        List<Poster> postersList = posterService.findAll();
        return ResponseEntity.ok(postersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poster> getPoster(@PathVariable Long id) {
        var poster = posterService.findById(id);
        return ResponseEntity.ok(poster);
    }

    // prideti mapinga su userId, ir pakeisti visus {id} i aiskius id kokio entity imame
    @PostMapping
    public ResponseEntity<Poster> createPoster(@RequestBody PosterRequest post) throws URISyntaxException {
        var user = userService.findById(post.getUserId());

        Poster poster = Poster.builder()
                .postName(post.getPostName())
                .categoryA(post.getCategoryA())
                .categoryB(post.getCategoryB())
                .description(post.getDescription())
                .status(post.getStatus())
                .user(user)
                .phoneNumber(post.getPhoneNumber())
                .city(post.getCity())
                .website(post.getWebsite())
                .videoLink(post.getVideoLink())
                .build();

        Poster savedPoster = posterService.save(poster);
        return ResponseEntity.ok(savedPoster);
    }

    @PutMapping("/{posterId}")
    public ResponseEntity<Poster> updatePoster(@PathVariable Long posterId, @RequestBody PosterRequest post) {
        var user = userService.findById(post.getUserId());

        Poster poster = Poster.builder()
                .id(posterId)
                .postName(post.getPostName())
                .categoryA(post.getCategoryA())
                .categoryB(post.getCategoryB())
                .description(post.getDescription())
                .status(post.getStatus())
                .user(user)
                .phoneNumber(post.getPhoneNumber())
                .city(post.getCity())
                .website(post.getWebsite())
                .videoLink(post.getVideoLink())
                .build();

        Poster updatedPoster = posterService.save(poster);
        return ResponseEntity.ok(updatedPoster);
    }

    @DeleteMapping("/{id}")
    public void deletePoster(@PathVariable Long id) {
        posterService.deleteById(id);
    }

    @GetMapping("/get/search")
    public ResponseEntity<List<Poster>> getSearch(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "priceIsAscending", required = false) Boolean priceIsAscending,
            @RequestParam(name = "createdAt", required = false) Boolean createdAt,
            @RequestParam(name = "updatedAt", required = false) Boolean updatedAt
    ) {
        List<Poster> posterList = posterService.searchEngine(
                category,
                type,
                status,
                city,
                priceIsAscending,
                createdAt,
                updatedAt
        );
        return ResponseEntity.ok(posterList);
    }

    @GetMapping("/seed-demo")
    public ResponseEntity<ResponseMessage> seed() {
        var poster1 = Poster.builder()
                .postName("Posters1")
                .categoryA(CategoryA.KOMPIUTERIJA)
                .categoryB(CategoryB.KOMPIUTERIAI)
                .description("Description1")
                .status(Status.ACTIVE)
                .user(userService.findByUsername("admin"))
                .city(City.ALYTUS)
                .website("WEBSITE")
                .videoLink("VIDEO")
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .price(1L)
                .build();
        posterService.save(poster1);

        var poster2 = Poster.builder()
                .postName("Posters2")
                .categoryA(CategoryA.KOMPIUTERIJA)
                .categoryB(CategoryB.ISORINIAI_IRENGINIAI)
                .description("Description2")
                .status(Status.NOTACTIVE)
                .user(userService.findByUsername("admin"))
                .city(City.KLAIPEDA)
                .website("WEBSITE")
                .videoLink("VIDEO")
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .price(2L)
                .build();
        posterService.save(poster2);

        var poster3 = Poster.builder()
                .postName("Posters3")
                .categoryA(CategoryA.KOMPIUTERIJA)
                .categoryB(CategoryB.KOMPIUTERIAI)
                .description("Description1")
                .status(Status.RESERVED)
                .user(userService.findByUsername("admin"))
                .city(City.KAUNAS)
                .website("WEBSITE")
                .videoLink("VIDEO")
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .price(3L)
                .build();
        posterService.save(poster3);

        var poster4 = Poster.builder()
                .postName("Posters4")
                .categoryA(CategoryA.TECHNIKA)
                .categoryB(CategoryB.VIDEO)
                .description("Description4")
                .status(Status.ACTIVE)
                .user(userService.findByUsername("admin"))
                .city(City.ALYTUS)
                .website("WEBSITE")
                .videoLink("VIDEO")
                .createdAt(LocalDateTime.of(2024,1,1,1,1,1,1))
                .updatedAt(null)
                .price(4L)
                .build();
        posterService.save(poster4);

        var poster5 = Poster.builder()
                .postName("Posters5")
                .categoryA(CategoryA.TECHNIKA)
                .categoryB(CategoryB.TECHNIKA_KITA)
                .description("Description1")
                .status(Status.RESERVED)
                .user(userService.findByUsername("admin"))
                .city(City.JONAVA)
                .website("WEBSITE")
                .videoLink("VIDEO")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.of(2024,1,1,1,1,1,1))
                .price(5L)
                .build();
        posterService.save(poster5);

        var poster6 = Poster.builder()
                .postName("Posters6")
                .categoryA(CategoryA.TECHNIKA)
                .categoryB(CategoryB.SODUI)
                .description("Description1")
                .status(Status.ACTIVE)
                .user(userService.findByUsername("admin"))
                .city(City.KAUNAS)
                .website("WEBSITE")
                .videoLink("VIDEO")
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .price(6L)
                .build();
        posterService.save(poster6);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("SEED"));
    }
}
