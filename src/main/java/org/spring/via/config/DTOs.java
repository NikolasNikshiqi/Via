package org.spring.via.config;

import org.spring.via.Models.Chat;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

public class DTOs {
    public record LoginCredentials(String email, String password) {
    }

    public record SignUpCredentials(String username,String email, String password) {}

    public record DisplayedChat(String contactUsername, UUID chatId ){
    }
}
