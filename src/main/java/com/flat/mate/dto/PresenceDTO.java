package com.flat.mate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresenceDTO {
    private UUID id;
    private UUID userId;
    private boolean inFlat;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
