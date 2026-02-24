package org.spring.via.config;

import org.springframework.context.annotation.Bean;

public class DTOs {
    public record LoginCredentials(String email, String password) {
    }

    public record SignUpCredentials(String username,String email, String password) {}
}
