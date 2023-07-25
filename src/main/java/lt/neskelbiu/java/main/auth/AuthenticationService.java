package lt.neskelbiu.java.main.auth;

import lt.neskelbiu.java.main.config.JwtService;
import lt.neskelbiu.java.main.exceptions.UserAlreadyExistsException;
import lt.neskelbiu.java.main.token.*;
import lt.neskelbiu.java.main.user.Role;
import lt.neskelbiu.java.main.user.User;
import lt.neskelbiu.java.main.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.config.JwtService;
import lt.neskelbiu.java.main.token.RefreshTokenRepository;
import lt.neskelbiu.java.main.token.TokenRepository;
import lt.neskelbiu.java.main.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (request.getRole() == null)
            request.setRole(Role.USER);

        if (repository.findByUsername(request.getUsername()).isPresent())
            throw(new UserAlreadyExistsException("User with this username already exists: " + request.getUsername()));

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .createdAt(LocalDate.now())
                .build();

        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        saveUserRefreshToken(savedUser, refreshToken);
        return  AuthenticationResponse.builder()
                .role(request.getRole().toString())
                .accessToken(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens= tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void revokeAllUserRefreshTokens(User user) {
        var validUserTokens= refreshTokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
        });
        refreshTokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        revokeAllUserRefreshTokens(user);
        saveUserToken(user, jwtToken);
        saveUserRefreshToken(user, refreshToken);
        return  AuthenticationResponse.builder()
                .role(user.getRole().toString())
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveUserRefreshToken(User user, String jwtToken) {
        var token = RefreshToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .build();
        refreshTokenRepository.save(token);
    }

    public AuthenticationResponse refresh(
            RefreshTokenRequest request
    ) {
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        var validUserTokens= refreshTokenRepository.findAllValidTokenByUser(user.getId());
        RefreshToken validToken = validUserTokens.stream().findFirst().orElseThrow();
        String refreshToken = validToken.getToken();

        if (jwtService.isTokenValid(refreshToken, user)) {
            var accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            return AuthenticationResponse.builder()
                    .role(user.getRole().toString())
                    .accessToken(accessToken)
                    .build();
        }
        return AuthenticationResponse.builder()
                .accessToken(null)
                .build();
    }
}
