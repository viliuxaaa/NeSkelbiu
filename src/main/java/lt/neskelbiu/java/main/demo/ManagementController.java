package lt.neskelbiu.java.main.demo;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.message.ResponseMessage;
import lt.neskelbiu.java.main.poster.PosterService;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserResponse;
import lt.neskelbiu.java.main.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management Controller")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ManagementController {

    private final  UserService userService;
    private final PosterService posterService;

    @Operation(
            summary = "Get all users",
            description = "With this endpoint you get all information about all users"
    )
    @GetMapping("/get/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        List<UserResponse> userResponseList = userService.getResponseAllUsers(userList);
        return ResponseEntity.ok(userResponseList);
    }

    @Operation(
            summary = "Delete single user poster",
            description = "With this endpoint you delete user's poster, you need to provide poster id."
    )
    @DeleteMapping("/get/{posterId}/delete-poster")
    public ResponseEntity deletePoster(@PathVariable Long posterId) {
        posterService.deleteById(posterId);
        return ResponseEntity.ok().body(new ResponseMessage("Poster with id " + posterId + " is deleted"));
    }

    @Operation(
            summary = "get endpoint for managment",
            description = "This is summary for managment get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    @Hidden
    public String get() {
        return "GET:: management controller";
    }

    @PostMapping
    @Hidden
    public String post() {
        return "POST:: management controller";
    }

    @PutMapping
    @Hidden
    public String put() {
        return "PUT:: management controller";
    }

    @DeleteMapping
    @Hidden
    public String delete() {
        return "DELETE:: management controller";
    }
}
