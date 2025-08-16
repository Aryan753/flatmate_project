package com.flat.mate.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LocationRequest {
    private UUID userId;
    private double latitude;
    private double longitude;
}
