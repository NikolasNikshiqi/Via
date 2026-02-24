package org.spring.via.seeders;

import jakarta.transaction.Transactional;
import org.spring.via.Enums.Role;
import org.spring.via.Models.User;
import org.spring.via.Repositories.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
public class UserSeeder {

    private final PasswordEncoder bcrypt;
    private final UserRepo userRepo;

    public UserSeeder(PasswordEncoder bcrypt, UserRepo userRepo) {
        this.bcrypt = bcrypt;
        this.userRepo = userRepo;
    }

    @Bean
    @Transactional
    CommandLineRunner seedUsers() {
        return args -> {
            if (userRepo.count() == 0) {
                User admin = new User("admin", "admin@hotmail.com", bcrypt.encode("admin123"), Role.ADMIN);
                User user1 = new User("user1", "user1@hotmail.com", bcrypt.encode("user123"), Role.USER);
                User user2 = new User("user2", "user2@hotmail.com", bcrypt.encode("user123"), Role.USER);
                User user3 = new User("user3", "user3@hotmail.com", bcrypt.encode("user123"), Role.USER);

                userRepo.save(user1);
                userRepo.save(user2);
                userRepo.save(user3);
                userRepo.save(admin);

                admin.getContacts().add(user1);

                admin.getContacts().add(user2);

                admin.getContacts().add(user3);

                userRepo.save(admin);
                System.out.println("Seeding successful!" + admin.getContacts());
            }
        };
    }
}
