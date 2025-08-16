package com.flat.mate.service;

import com.flat.mate.dto.MessageDTO;
import com.flat.mate.exception.CustomException;
import com.flat.mate.model.Message;
import com.flat.mate.model.User;
import com.flat.mate.repository.MessageRepository;
import com.flat.mate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageDTO sendMessage(MessageDTO dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new CustomException("Sender not found"));
        User receiver = null;
        if (dto.getReceiverId() != null) {
            receiver = userRepository.findById(dto.getReceiverId())
                    .orElseThrow(() -> new CustomException("Receiver not found"));
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(dto.getContent());
        message.setType(dto.getType());
        message.setTimestamp(LocalDateTime.now());

        Message saved = messageRepository.save(message);
        return mapToDTO(saved);
    }

    public List<MessageDTO> getMessagesForUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        List<Message> sent = messageRepository.findBySender(user);
        List<Message> received = messageRepository.findByReceiver(user);

        return Stream.concat(sent.stream(), received.stream())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private MessageDTO mapToDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getSender().getId(),
                message.getReceiver() != null ? message.getReceiver().getId() : null,
                message.getContent(),
                message.getType(),
                message.getTimestamp()
        );
    }
}
