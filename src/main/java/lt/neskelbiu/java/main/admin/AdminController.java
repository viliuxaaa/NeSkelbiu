package lt.neskelbiu.java.main.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.auth.AuthenticationResponse;
import lt.neskelbiu.java.main.auth.AuthenticationService;
import lt.neskelbiu.java.main.auth.RegisterRequest;
import lt.neskelbiu.java.main.poster.PosterService;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserResponse;
import lt.neskelbiu.java.main.user.UserService;
import lt.neskelbiu.java.main.message.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin Controller")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AuthenticationService service;
    private final PosterService posterService;

    @Operation(
            summary = "Register new admin (body = RegisterRequest)",
            description = "With this endpoint you register new admin, you have to provide RegisterRequest body."
    )
    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request, Role.ADMIN));
    }

    @Operation(
            summary = "Used for locking or unlocking user's account",
            description = "With this endpoint you lock or unlock users account, you have to provide user id, which you want to lock/unlock. Does not work for admins"
    )
    @PutMapping("/get/{userId}/change-lock")
    public ResponseEntity<ResponseMessage> changeLock(@PathVariable Long userId) {
        User user = userService.findById(userId);

        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Admin cannot be locked"));
        }

        String message = userService.changeUserLockdownState(userId);

        return ResponseEntity.ok().body(new ResponseMessage("User with id " + userId + " is " + message));
    }

    @Operation(
            summary = "Used for deleting user account",
            description = "With this endpoint delete user account. Need to provide user id. Does not work for admins"
    )
    @DeleteMapping("/get/{userId}/delete")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable Long userId) {
        User user = userService.findById(userId);

        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Admin cannot be deleted"));
        }

        userService.deleteUser(userId);

        return ResponseEntity.ok().body(new ResponseMessage("User with id " + userId + " is deleted"));
    }

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
}
