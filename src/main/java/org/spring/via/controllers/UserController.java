package org.spring.via.controllers;

import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.spring.via.Models.User;
import org.spring.via.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/contacts")
    public Set<User> getContacts(@AuthenticationPrincipal User currentUser){
        return userService.getContacts(currentUser);
    }

    @GetMapping("/profile")
    public User getProfile(@AuthenticationPrincipal User currentUser){
        return userService.getProfile(currentUser);
    }

    @PostMapping("/add_contact")
    public ResponseEntity<String> addContact(@AuthenticationPrincipal User currentUser,@RequestParam String email){
        return userService.addContact(currentUser,email);
    }
}
