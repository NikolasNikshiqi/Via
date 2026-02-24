package org.spring.via.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.spring.via.Enums.MsgStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    private String text;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private MsgStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Message(String text, LocalDateTime timestamp, MsgStatus status, User sender, Chat chat) {
        this.text = text;
        this.timestamp = timestamp;
        this.status = status;
        this.sender = sender;
        this.chat = chat;
    }

    public Message() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MsgStatus getStatus() {
        return status;
    }

    public void setStatus(MsgStatus status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
