package lt.neskelbiu.java.main.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.exceptions.UserAlreadyExistsExceptionEmail;
import lt.neskelbiu.java.main.exceptions.UserAlreadyExistsExceptionUsername;
import lt.neskelbiu.java.main.message.ResponseMessage;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth") //by default aprasytas
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Value("${application.security.jwt.expiration}")
    private int jwtExpiration;

    private final AuthenticationService service;

    @Operation(
            description = "Used for getting registering user",
            summary = "With this endpoint you register user. Send -(body = RegisterRequest), get (body = AuthenticationResponse)"
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(service.register(request, Role.USER));
        } catch (UserAlreadyExistsExceptionUsername e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("This username already exists"));
        } catch (UserAlreadyExistsExceptionEmail e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("This email already exists"));
        }
    }

    @Operation(
            description = "Used for getting authenticating user",
            summary = "With this endpoint you authenticate user. Send - (body = AuthenticationRequest), get (body = AuthenticationResponse)"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (
            @RequestBody AuthenticationRequest request,
            HttpServletResponse httpServletResponse
    ) {
        AuthenticationResponse response = service.authenticate(request);

        Cookie cookie1 = new Cookie("accessToken", response.getAccessToken());
        cookie1.setHttpOnly(true);
        cookie1.setSecure(false);
        cookie1.setMaxAge(jwtExpiration / 1000);
        cookie1.setPath("/");
        httpServletResponse.addCookie(cookie1);

        Cookie cookie2 = new Cookie("userId", String.valueOf(response.getId()) );
        cookie2.setHttpOnly(false);
        cookie2.setSecure(false);
        cookie2.setMaxAge(jwtExpiration / 1000);
        cookie2.setPath("/");
        httpServletResponse.addCookie(cookie2);

        Cookie cookie3 = new Cookie("user", String.valueOf(response.getUsername()) );
        cookie3.setHttpOnly(false);
        cookie3.setSecure(false);
        cookie3.setMaxAge(jwtExpiration / 1000);
        cookie3.setPath("/");
        httpServletResponse.addCookie(cookie3);

        Cookie cookie4 = new Cookie("email", String.valueOf(response.getEmail()) );
        cookie4.setHttpOnly(false);
        cookie4.setSecure(false);
        cookie4.setMaxAge(jwtExpiration / 1000);
        cookie4.setPath("/");
        httpServletResponse.addCookie(cookie4);

        Cookie cookie5 = new Cookie("roles", String.valueOf(response.getRole()) );
        cookie5.setHttpOnly(false);
        cookie5.setSecure(false);
        cookie5.setMaxAge(jwtExpiration / 1000);
        cookie5.setPath("/");
        httpServletResponse.addCookie(cookie5);

        Cookie cookie6 = new Cookie("expire", String.valueOf((new Date(System.currentTimeMillis() + refreshExpiration)).getTime()) );
        cookie6.setHttpOnly(false);
        cookie6.setSecure(false);
        cookie6.setPath("/");
        httpServletResponse.addCookie(cookie6);


        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Used for getting refresh token",
            summary = "With this endpoint you refresh user's access token. Send - (body = RefreshTokenRequest), get (body = AuthenticationResponse)"
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh (
            @RequestBody RefreshTokenRequest request,
            HttpServletResponse httpServletResponse
    ) {
        AuthenticationResponse response = service.refresh(request);

        Cookie cookie1 = new Cookie("accessToken", response.getAccessToken());
        cookie1.setHttpOnly(true);
        cookie1.setSecure(false);
        cookie1.setMaxAge(jwtExpiration / 1000);
        cookie1.setPath("/");
        httpServletResponse.addCookie(cookie1);

        Cookie cookie2 = new Cookie("userId", String.valueOf(response.getId()) );
        cookie2.setHttpOnly(false);
        cookie2.setSecure(false);
        cookie2.setMaxAge(jwtExpiration / 1000);
        cookie2.setPath("/");
        httpServletResponse.addCookie(cookie2);

        Cookie cookie3 = new Cookie("user", String.valueOf(response.getUsername()) );
        cookie3.setHttpOnly(false);
        cookie3.setSecure(false);
        cookie3.setMaxAge(jwtExpiration / 1000);
        cookie3.setPath("/");
        httpServletResponse.addCookie(cookie3);

        Cookie cookie4 = new Cookie("user", String.valueOf(response.getEmail()) );
        cookie4.setHttpOnly(false);
        cookie4.setSecure(false);
        cookie4.setMaxAge(jwtExpiration / 1000);
        cookie4.setPath("/");
        httpServletResponse.addCookie(cookie4);

        Cookie cookie5 = new Cookie("roles", String.valueOf(response.getRole()) );
        cookie5.setHttpOnly(false);
        cookie5.setSecure(false);
        cookie5.setMaxAge(jwtExpiration / 1000);
        cookie5.setPath("/");
        httpServletResponse.addCookie(cookie5);

        return ResponseEntity.ok(response);
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
