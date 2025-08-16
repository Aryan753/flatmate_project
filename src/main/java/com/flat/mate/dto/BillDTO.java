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
public class BillDTO {
    private UUID id;
    private String type; // electricity or daily material
    private double amount;
    private LocalDate date;
    private List<UUID> participantIds;
    private String splitMethod; // equal or by_hours
}
