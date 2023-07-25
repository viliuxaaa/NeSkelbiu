package lt.neskelbiu.java.main.poster;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.user.UserRepository;
import lt.neskelbiu.java.main.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.neskelbiu.java.main.poster.Poster;
import lt.neskelbiu.java.main.poster.PosterService;

@RestController
@RequestMapping("/api/posters") // Base URL mapping for all methods in this controller
@RequiredArgsConstructor
public class PosterController {

    final private PosterService posterService;
    final private UserService userService;

    @GetMapping
    public ResponseEntity<List<Poster>> getAllPosters() {

        List<Poster> postersList = posterService.findAll();
        return ResponseEntity.ok(postersList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poster> getPoster(@PathVariable Long id) {
        var poster = posterService.findById(id);
        return ResponseEntity.ok(poster);
    }

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
        return ResponseEntity.ok(poster);
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
}
