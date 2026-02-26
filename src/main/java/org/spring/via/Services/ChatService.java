package org.spring.via.Services;

import jakarta.transaction.Transactional;
import org.spring.via.Models.Chat;
import org.spring.via.Models.User;
import org.spring.via.Repositories.ChatRepo;
import org.spring.via.Repositories.UserRepo;
import org.spring.via.errors.UserNotContactException;
import org.spring.via.errors.UserNotFoundException;
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

    @Transactional
    public ResponseEntity<String> createChat(User authedUser, Integer contactId) {
        User user1 = userRepo.findById(authedUser.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        User user2 = userRepo.findById(contactId).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(user1.getContacts() == null || !user1.getContacts().contains(user2)){ //If user is not in contact list
            throw new UserNotContactException("User is not a contact of yours");
        }
        Chat chat = new Chat(false,List.of(user1,user2));
        chatRepo.save(chat);

        user1.getChats().add(chat);
        user2.getChats().add(chat);

        userRepo.save(user1);
        userRepo.save(user2);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public List<Chat> getChats(User authedUser) {
        User user = userRepo.findById(authedUser.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getChats().size();
        return user.getChats();
    }
}
