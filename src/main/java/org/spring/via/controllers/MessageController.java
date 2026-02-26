package org.spring.via.controllers;

import org.spring.via.Models.Message;
import org.spring.via.Models.User;
import org.spring.via.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/msg")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage(@AuthenticationPrincipal User currentUser,
                                      @RequestParam UUID chatId,
                                      @RequestParam String text) {
       return messageService.sendMessage(currentUser,chatId,text);
    }

    @GetMapping("/chatMessages")
    public List<Message> getMessages(@RequestParam UUID chatId, @AuthenticationPrincipal User currentUser) {
        return messageService.getMessagesInChat(chatId, currentUser.getId());
    }

}
