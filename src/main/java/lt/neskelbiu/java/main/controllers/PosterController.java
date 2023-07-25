package lt.neskelbiu.java.main.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
public class PosterController {
    @Autowired
    private PosterService posterService;

    @GetMapping
    public List<Poster> getAllPosters() {
        return posterService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poster> getPoster(@PathVariable Long id) {
        Optional<Poster> poster = Optional.of(posterService.findById(id));
        return poster.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Poster> createPoster(@RequestBody Poster post) throws URISyntaxException {
        Poster savedPoster = posterService.save(post);
        return ResponseEntity.created(new URI("/api/posters/" + savedPoster.getId())).body(savedPoster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poster> updatePoster(@PathVariable Long id, @RequestBody Poster updatedPoster) {
        Optional<Poster> existingPoster = Optional.of(posterService.findById(id));

        if (existingPoster.isPresent()) {
            updatedPoster.setId(id); // Ensure the ID is set correctly for the updated poster.
            Poster savedPoster = posterService.save(updatedPoster);
            return ResponseEntity.ok(savedPoster);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoster(@PathVariable Long id) {
        Optional<Poster> existingPoster = Optional.of(posterService.findById(id));

        if (existingPoster.isPresent()) {
            posterService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
