package org.spring.via.Repositories;

import org.spring.via.Models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepo extends JpaRepository<Chat, UUID> {

}
