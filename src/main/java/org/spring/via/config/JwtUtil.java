package org.spring.via.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final long EXPIRATION_TIME = 1000 * 3600 * 8;
    private final String SECRET_KEY = "818fe65a4442476692e5b958344c88af494c86763048ce81bad36f9949c98384";
    private final Key KEY =  Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public boolean validateToken(String token, String email) {
        final String tokenEmail = extractEmail(token);
        return tokenEmail.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return parseToken(token).getBody().getExpiration().before(new Date());
    }

    private Jws<Claims> parseToken (String token){
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
    }
}
