package org.spring.via.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    private Boolean groupChat;

    @ManyToMany
    private List<User> members =  new ArrayList<>();

    @OneToMany (mappedBy = "chat", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages = new ArrayList<>();

    public Chat(Boolean groupChat, List<User> members) {
        this.groupChat = groupChat;
        this.members = members;
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
