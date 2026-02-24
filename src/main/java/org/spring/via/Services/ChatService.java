package org.spring.via.Services;

import org.spring.via.Models.Chat;
import org.spring.via.Models.User;
import org.spring.via.Repositories.ChatRepo;
import org.spring.via.Repositories.UserRepo;
import org.spring.via.errors.UserNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {


    private final ChatRepo chatRepo;
    private final UserRepo userRepo;

    public ChatService(ChatRepo chatRepo, UserRepo userRepo) {
        this.chatRepo = chatRepo;
        this.userRepo = userRepo;
    }


    public ResponseEntity<String> createChat(User authedUser, Integer contactId) {
        User user1 = userRepo.findById(authedUser.getId()).orElseThrow(() -> new UserNotFound("User not found"));
        User user2 = userRepo.findById(contactId).orElseThrow(() -> new UserNotFound("User not found"));
        Chat chat = new Chat(false,List.of(user1,user2));

        user1.getChats().add(chat);
        user2.getChats().add(chat);

        userRepo.save(user1);
        userRepo.save(user2);
        chatRepo.save(chat);
        return ResponseEntity.ok().build();
    }
}
