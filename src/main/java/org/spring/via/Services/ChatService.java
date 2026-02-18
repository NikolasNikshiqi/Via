package org.spring.via.Services;

import org.spring.via.Repositories.ChatRepo;
import org.springframework.stereotype.Service;

@Service
public class ChatService {


    private final ChatRepo chatRepo;

    public ChatService(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }


}
