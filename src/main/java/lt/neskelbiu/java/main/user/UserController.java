package lt.neskelbiu.java.main.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.message.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user") // Base URL mapping for all methods in this controller
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Used for getting user's profile info.",
            description = "With this endpoint you get user's profile info"
    )
    @GetMapping("/get/{userId}/profile")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable Long userId) throws InterruptedException {
        var user = userService.findById(userId);
        var responseUser = userService.getResponseUser(user);
        return ResponseEntity.ok(responseUser);
    }

    @Operation(
            summary = "Used for changing first name and last name for user's account, used by users with role - USER",
            description = "With this endpoint user updates it's account. This is protected path, for authenticated users only."
    )
    @PostMapping("/{userId}/change-name")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ResponseMessage> changeName(
            @PathVariable Long userId,
            @RequestBody ChangeNameRequest changeNameRequest
    ) {
        userService.changeName(userId, changeNameRequest);
        String message = "User updated successfully";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

    @Operation(
            summary = "Used for deleting user account, used by users with role - USER",
            description = "With this endpoint user delete it's account. This is protected path, for authenticated users only."
    )
    @DeleteMapping("/{userId}/user-delete")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        String message = "User deleted successfully";
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }
}
