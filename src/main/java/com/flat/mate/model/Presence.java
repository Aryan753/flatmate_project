package com.flat.mate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "presence")
public class Presence {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    private boolean inFlat; // boolean field

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    // Calculate hours automatically
    public double getTotalHours() {
        if (entryTime == null) return 0;
        LocalDateTime endTime = exitTime != null ? exitTime : LocalDateTime.now();
        return java.time.Duration.between(entryTime, endTime).toMinutes() / 60.0;
    }
}