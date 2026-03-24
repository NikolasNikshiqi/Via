package org.spring.via.config;

import org.spring.via.Repositories.UserRepo;
import org.spring.via.Services.BlacklistTokens;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepo userRepository;
    private final BlacklistTokens blacklistTokens;

    public JwtFilter(JwtUtil jwtUtil, UserRepo userRepository, BlacklistTokens blacklistTokens) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.blacklistTokens = blacklistTokens;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);

            userRepository.findByEmail(email).ifPresent(user -> {
                if (jwtUtil.validateToken(token, user.getEmail()) && !blacklistTokens.isBlacklisted(token)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            });
        }

        filterChain.doFilter(request, response);
    }
}
