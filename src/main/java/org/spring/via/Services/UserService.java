package org.spring.via.Services;

import org.spring.via.Models.User;
import org.spring.via.Repositories.UserRepo;
import org.spring.via.errors.UserNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Service
public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Set<User> getContacts(User currentUser) {
        User user = userRepo.findById(currentUser.getId()).orElseThrow(IllegalStateException::new);
        return user.getContacts();
    }

    public User getProfile(User user) {
        return userRepo.findById(user.getId()).orElseThrow(IllegalStateException::new);
    }

    public ResponseEntity<String> addContact(User authedUser, String email){
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UserNotFound("User does not exist"));
        User currentUser = userRepo.findById(authedUser.getId()).orElseThrow(IllegalStateException::new);
        currentUser.getContacts().add(user);
        userRepo.save(currentUser);
        return ResponseEntity.ok().build();
    }
}
