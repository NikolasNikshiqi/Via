package org.spring.via.controllers;

import org.spring.via.Models.Chat;
import org.spring.via.Models.User;
import org.spring.via.Services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createChat(@AuthenticationPrincipal User currentUser, @RequestParam Integer contactId) {
        return chatService.createChat(currentUser,contactId);
    }

    @GetMapping("/chats")
    public List<Chat> getChats(@AuthenticationPrincipal User currentUser) {
        return chatService.getChats(currentUser);
    }

}
