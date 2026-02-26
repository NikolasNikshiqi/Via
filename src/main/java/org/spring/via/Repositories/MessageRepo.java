package org.spring.via.Repositories;

import org.spring.via.Models.Chat;
import org.spring.via.Models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepo extends JpaRepository<Message, UUID> {

    @Query("""
          SELECT m from Message m
              WHERE m.chat.uuid = :chatId
                  AND m.status = 'SENT'
                      AND m.sender.id = :userId
    """
    )
    List<Message> findUndelievered(UUID chatId, Integer userId);
}
