package com.example.effectivemobiletest.configuration;

import com.example.effectivemobiletest.entity.User;
import com.example.effectivemobiletest.exception.AuthenticationException;
import com.example.effectivemobiletest.service.JwtService;
import com.example.effectivemobiletest.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Getter
    private final List<RequestMatcher> ignoredPaths = List.of(
            new AntPathRequestMatcher("/auth/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/swagger-resources/*"),
            new AntPathRequestMatcher("/v3/api-docs/**")
    );
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (checkIgnoredPaths(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HEADER_NAME);

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            throw new AuthenticationException();
        }

        String jwt = header.substring(BEARER_PREFIX.length());
        String email = jwtService.extractEmail(jwt);

        if (email != null && !email.isEmpty()
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.getUserByEmail(email);

            if (jwtService.isTokenValid(jwt, user)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,
                        null, user.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkIgnoredPaths(HttpServletRequest request) {
        for (RequestMatcher matcher : ignoredPaths) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}
