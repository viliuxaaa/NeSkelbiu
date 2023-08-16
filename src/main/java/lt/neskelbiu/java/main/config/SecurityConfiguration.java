package lt.neskelbiu.java.main.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.function.Supplier;

import static lt.neskelbiu.java.main.user.Permission.*;
import static lt.neskelbiu.java.main.user.Role.ADMIN;
import static lt.neskelbiu.java.main.user.Role.MANAGER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final WebSecurity webSecurity;

    public AuthorizationDecision checkIfAuthorized(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        String userId = context.getVariables().get("userId");
        boolean granted = webSecurity.checkUserId(authentication, userId);
        return new AuthorizationDecision(granted);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http

    ) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/authenticate",
                                "/api/v1/auth/logout"
                        ).permitAll()
                        .requestMatchers(
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        //admin
                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

                        //manager
                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                        //user
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/change-password/{userId}").access(this::checkIfAuthorized)
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/{userId}/change-name").access(this::checkIfAuthorized)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user/{userId}/user-delete").access(this::checkIfAuthorized)

                        //userImg
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/get/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/user/{userId}/**").access(this::checkIfAuthorized)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user/{userId}/**").access(this::checkIfAuthorized)

                        //poster
                        .requestMatchers(HttpMethod.GET, "/api/v1/poster/get/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/poster/{userId}/**").access(this::checkIfAuthorized)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/poster/{userId}/**").access(this::checkIfAuthorized)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/poster/{userId}/**").access(this::checkIfAuthorized)

                        //posterImg
                        .requestMatchers(HttpMethod.GET, "/api/v1/images/poster/get/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/images/poster/{userId}/**").access(this::checkIfAuthorized)
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/images/poster/{userId}/**").access(this::checkIfAuthorized)

                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .deleteCookies("accessToken", "userId", "user", "email", "roles")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) ->
                                        SecurityContextHolder.clearContext()
                        )
                );

        return http.build();
    }
}
