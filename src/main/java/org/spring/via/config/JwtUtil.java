package org.spring.via.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

@Component
public class JwtUtil {

    private final long EXPIRATION_TIME ;
    private String SECRET_KEY;
    private Key KEY;

    public JwtUtil() {
        try{
            FileReader reader = new FileReader(".env");
            Properties properties = new Properties();
            properties.load(reader);

            this.SECRET_KEY = properties.getProperty("SECRET_KEY");

            this.KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.EXPIRATION_TIME = 1000 * 3600 * 8;
    }

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

    public Date extractExpiration(String token){
        return parseToken(token).getBody().getExpiration();
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
