package lt.neskelbiu.java.main.poster;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.poster.categories.CategoryA;
import lt.neskelbiu.java.main.poster.categories.CategoryB;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/poster") // Base URL mapping for all methods in this controller
@Tag(name = "Poster Controller")
@RequiredArgsConstructor
public class PosterController {

    final private PosterService posterService;

    @Operation(
            summary = "Used for getting all poster"
    )
    @GetMapping("/get/all")
    public ResponseEntity<List<PosterResponse>> getAllPosters() {
        List<Poster> posterList = posterService.findAll();
        List<PosterResponse> postersList = posterService.posterListResponse(posterList);
        return ResponseEntity.ok(postersList);
    }

    @Operation(
            summary = "Used for getting all of single user's poster",
            description = "With this endpoint you can get all of single user's posters. You need to provide user id."
    )
    @GetMapping("/get/{userId}/all")
    public ResponseEntity<List<PosterResponse>> getAllUsersPosters(
            @PathVariable Long userId
    ) {
        List<Poster> posterList = posterService.findAllUsersPosters(userId);
        List<PosterResponse> postersList = posterService.posterListResponse(posterList);
        return ResponseEntity.ok(postersList);
    }

    @Operation(
            summary = "Used for getting single poster",
            description = "With this endpoint user can get poster. You need to provide poster id."
    )
    @GetMapping("/get/{posterId}")
    public ResponseEntity<PosterResponse> getPoster(
            @PathVariable Long posterId
    ) {
        Poster poster = posterService.findById(posterId);
        PosterResponse posterResponse = posterService.buildPosterResponse(poster);
        return ResponseEntity.ok(posterResponse);
    }

    @Operation(
            summary = "Used for creating user's poster",
            description = "With this endpoint user can create his own poster. This is protected path, " +
                    "for authenticated users only.\""
    )
    @PostMapping("/{userId}/create")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PosterResponse> createPoster(
            @PathVariable Long userId,
            @RequestBody PosterRequest post
    ) {
        Poster poster = posterService.buildPosterNoId(userId, post);
        posterService.save(poster);
        PosterResponse posterResponse = posterService.buildPosterResponse(poster);
        return ResponseEntity.ok(posterResponse);
    }

    @Operation(
            summary = "Used for updating user's poster",
            description = "With this endpoint user can update his own poster. You need to provide poster id. This is protected path, " +
                    "for authenticated users only.\""
    )
    @PutMapping("/{userId}/update/{posterId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PosterResponse> updatePoster(
            @PathVariable Long userId,
            @PathVariable Long posterId,
            @RequestBody PosterRequest post
    ) {
        Poster poster = posterService.buildPosterWithId(userId, posterId, post);
        posterService.save(poster);
        PosterResponse posterResponse = posterService.buildPosterResponse(poster);
        return ResponseEntity.ok(posterResponse);
    }

    @Operation(
            summary = "Used for getting latest 10 poster"
    )
    @GetMapping("/get/latest")
    public ResponseEntity<List<PosterResponse>> getLatest() {
        return ResponseEntity.ok(posterService.getLatest());
    }

    @Operation(
            summary = "Used for deleting user's poster",
            description = "With this endpoint user can delete his own poster. You need to provide poster id. This is protected path, " +
                    "for authenticated users only."
    )
    @DeleteMapping("/{userId}/delete/{posterId}")
    @SecurityRequirement(name = "bearerAuth")
    public void deletePoster(@PathVariable Long posterId) {
        posterService.deleteById(posterId);
    }

    @Operation(
            summary = "Used for searching specific posters",
            description = "With this endpoint you search for posters with paramaters. You search by category, type, city or status. These elements " +
                    "are enums and you have to check enums on back end server files. You can also order all found posters by providing " +
                    "priceIsAscending(true - from lowest to highest, false - from highest to lowest), by createdAt(true - order by recently " +
                    "created posters), by updatedAt(true - order by recently updated posters). For ordering list, you must use only single attribute " +
                    "between priceIsAscending, createdAt or updatedAt. Example: http://localhost:8080/api/v1/posters/get/search?city=alytus&status=active&category=a&type=kompiuterija&createdAt=true"
    )
    @GetMapping("/get/search")
    public ResponseEntity<List<PosterResponse>> getSearch(
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "city", required = false) List<String> city,
            @RequestParam(name = "priceIsAscending", required = false) Boolean priceIsAscending,
            @RequestParam(name = "createdAt", required = false) Boolean createdAt,
            @RequestParam(name = "updatedAt", required = false) Boolean updatedAt,
            @RequestParam(name = "string", required = false) String string
    ) {
        List<PosterResponse> posterList = posterService.searchEngine(
                category,
                type,
                city,
                priceIsAscending,
                createdAt,
                updatedAt,
                string
        );
        return ResponseEntity.ok(posterList);
    }
}
