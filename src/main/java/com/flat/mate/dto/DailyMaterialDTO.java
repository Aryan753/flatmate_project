package com.flat.mate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyMaterialDTO {
    private UUID id;
    private String name;
    private double cost;
    private UUID addedById;
    private LocalDate date;
    private List<UUID> sharedWithIds;
}
