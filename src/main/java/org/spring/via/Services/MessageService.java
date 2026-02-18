package org.spring.via.Services;

import org.spring.via.Repositories.MessageRepo;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }
}
