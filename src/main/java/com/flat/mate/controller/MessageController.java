package com.flat.mate.controller;

import com.flat.mate.dto.ApiResponse;
import com.flat.mate.dto.MessageDTO;
import com.flat.mate.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ApiResponse<MessageDTO>> sendMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO sentMessage = messageService.sendMessage(messageDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Message sent", sentMessage));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<MessageDTO>>> getMessages(@PathVariable UUID userId) {
        List<MessageDTO> messages = messageService.getMessagesForUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Messages fetched", messages));
    }
}
