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
@RequestMapping("/api/v1/poster") // Base URL mapping for all methods in this controller
@RequiredArgsConstructor
public class PosterController {

    final private PosterService posterService;
    final private UserService userService;

    @GetMapping("/get/all")
    public ResponseEntity<List<PosterResponse>> getAllPosters() {
        List<Poster> posterList = posterService.findAll();
        List<PosterResponse> postersList = posterService.posterListResponse(posterList);
        return ResponseEntity.ok(postersList);
    }

    @GetMapping("/get/{userId}/{posterId}")
    public ResponseEntity<PosterResponse> getPoster(
            @PathVariable Long posterId,
            @PathVariable Long userId
    ) {
        Poster poster = posterService.findById(posterId);
        PosterResponse posterResponse = posterService.buildPosterResponse(poster);
        return ResponseEntity.ok(posterResponse);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Poster> createPoster(
            @PathVariable Long userId,
            @RequestBody PosterRequest post
    ) {
        Poster poster = posterService.buildPosterNoId(userId, post);
        Poster savedPoster = posterService.save(poster);
        return ResponseEntity.ok(savedPoster);
    }

    @PutMapping("/update/{posterId}")
    public ResponseEntity<Poster> updatePoster(@PathVariable Long posterId, @RequestBody PosterRequest post) {
        Poster poster = posterService.buildPosterWithId(posterId, post);
        Poster updatedPoster = posterService.save(poster);
        return ResponseEntity.ok(updatedPoster);
    }

    @DeleteMapping("/delete/{posterId}")
    public void deletePoster(@PathVariable Long posterId) {
        posterService.deleteById(posterId);
    }

    @GetMapping("/get/search")
    public ResponseEntity<List<PosterResponse>> getSearch(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "priceIsAscending", required = false) Boolean priceIsAscending,
            @RequestParam(name = "createdAt", required = false) Boolean createdAt,
            @RequestParam(name = "updatedAt", required = false) Boolean updatedAt
    ) {
        List<PosterResponse> posterList = posterService.searchEngine(
                category,
                type,
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
