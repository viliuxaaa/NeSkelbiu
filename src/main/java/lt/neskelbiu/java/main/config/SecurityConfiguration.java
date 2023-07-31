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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/v1/auth/**",
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


                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/poster/get/{userId}/**").access(this::checkIfAuthorized)


                        .anyRequest()
                        .permitAll()
                        //.authenticated()
=======

                        //manager
                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                        //user
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/refresh").permitAll()

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

                        .anyRequest().authenticated()
>>>>>>> e788083e09d19bad4191a7db85458104ca7f65ad
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) ->
                                        SecurityContextHolder.clearContext()
                        )
                );

        return http.build();
    }
}
