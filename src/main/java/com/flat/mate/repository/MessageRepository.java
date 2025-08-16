package com.flat.mate.repository;

import com.flat.mate.model.Message;
import com.flat.mate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findBySender(User sender);
    List<Message> findByReceiver(User receiver);
}
