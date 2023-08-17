package lt.neskelbiu.java.main.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lt.neskelbiu.java.main.token.TokenRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component // @Service and @Repository extends @Component
@RequiredArgsConstructor    //creates constructor with any final field declared in class
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,     // we can intercept every request
            @NotNull HttpServletResponse response,   // and make an extract data from for example the request and provide new data within the response
            @NotNull FilterChain filterChain         // FilterChain - Chain of responsibility design pattern, so it will it contains the list of the other filters
            // that we need to execute. When we do the filter it will call the next filter within the chain.
    ) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;


        // Cookie Handle
//        Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies())
//                        .orElse(new Cookie[0]))
//                .filter(cookie -> "auth_by_cookie".equals(cookie.getName()))
//                .findFirst();
//
//        if(authHeader == null || !authHeader.startsWith("Bearer ") || !cookieAuth.isPresent()) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        // Token handle
//        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);


        Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies())
                        .orElse(new Cookie[0]))
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst();

        if(!cookieAuth.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie cookie = cookieAuth.get();
        jwt = cookie.getValue();
        username = jwtService.extractUsername(jwt); //extarct the username  (email) from JWT token that we got;

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
