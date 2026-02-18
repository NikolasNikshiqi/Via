package org.spring.via.Models;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    private Boolean groupChat;

    @ManyToMany
    private List<User> members;

    @OneToMany (mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages;

    public Chat(Boolean groupChat, List<User> members, List<Message> messages) {
        this.groupChat = groupChat;
        this.members = members;
        this.messages = messages;
    }

    public Chat() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(Boolean groupChat) {
        this.groupChat = groupChat;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
