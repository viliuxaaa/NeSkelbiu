package lt.neskelbiu.java.main.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.message.ResponseMessage;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth") //by default aprasytas
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(
            description = "Used for getting registering user",
            summary = "With this endpoint you register user. Send -(body = RegisterRequest), get (body = AuthenticationResponse)"
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request, Role.USER));
    }

    @Operation(
            description = "Used for getting authenticating user",
            summary = "With this endpoint you authenticate user. Send - (body = AuthenticationRequest), get (body = AuthenticationResponse)"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(
            description = "Used for getting refresh token",
            summary = "With this endpoint you refresh user's access token. Send - (body = RefreshTokenRequest), get (body = AuthenticationResponse)"
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh (
            @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(service.refresh(request));
    }

    @Operation(
            description = "Used for resetting user password",
            summary = "With this endpoint you reset user password. Send - (body = PasswordRequest), get (body = ResponseMessage)"
    )
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<ResponseMessage> changePassword (
            @RequestBody PasswordRequest request,
            @PathVariable Long userId
    ) {
        service.changePassword(request, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Password has been changed"));
    }
}
