package com.flat.mate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private UUID id;
    private UUID senderId;
    private UUID receiverId; // null if group message
    private String content;
    private String type; // text or call
    private LocalDateTime timestamp;
}
