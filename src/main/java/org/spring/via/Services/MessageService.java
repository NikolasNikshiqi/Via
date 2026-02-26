package org.spring.via.Services;

import jakarta.transaction.Transactional;
import org.spring.via.Enums.MsgStatus;
import org.spring.via.Models.Chat;
import org.spring.via.Models.Message;
import org.spring.via.Models.User;
import org.spring.via.Repositories.ChatRepo;
import org.spring.via.Repositories.MessageRepo;
import org.spring.via.Repositories.UserRepo;
import org.spring.via.errors.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final UserRepo userRepo;
    private final ChatRepo chatRepo;
    private MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo, UserRepo userRepo, ChatRepo chatRepo) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
    }

    @Transactional
    public ResponseEntity sendMessage(User authedUser, UUID chatId, String text) {

        User sender = userRepo.findById(authedUser.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Chat chat = chatRepo.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));

        Message message = new Message();

        message.setSender(sender);
        message.setChat(chat);
        message.setText(text);
        message.setStatus(MsgStatus.SENT);
        message.setTimestamp(LocalDateTime.now());

        messageRepo.save(message);

        chat.getMessages().add(message);
        chatRepo.save(chat);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public void getUndelievered(UUID chatId, Integer userId) {
        Chat chat = chatRepo.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        Integer recipientId = -1;
        for(User user : chat.getMembers()) {
            if(!user.getId().equals(userId)) {
                recipientId = user.getId();
            }
        }

        List<Message> messages = messageRepo.findUndelievered(chatId, recipientId);
        for (Message message : messages) {
            message.setStatus(MsgStatus.DELIVERED);
            messageRepo.save(message);
        }
    }

    @Transactional
    public List<Message> getMessagesInChat(UUID chatId, Integer userId) {
        getUndelievered(chatId, userId);

        Chat chat = chatRepo.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));

        chat.getMessages().size();
        return chat.getMessages();
    }
}

