package com.flat.mate.service;

import com.flat.mate.dto.PresenceDTO;
import com.flat.mate.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    // Flat coordinates (replace with your flat’s coordinates or fetch from DB)
    private static final double FLAT_LATITUDE = 28.6139;  // Example: Delhi
    private static final double FLAT_LONGITUDE = 77.2090;
    private static final double THRESHOLD_METERS = 20;    // 20 meters radius

    /**
     * Check if a user is within the flat boundary based on GPS.
     */
    public boolean isUserInFlat(double userLat, double userLng) {
        double distance = calculateDistance(FLAT_LATITUDE, FLAT_LONGITUDE, userLat, userLng);
        return distance <= THRESHOLD_METERS;
    }

    /**
     * Haversine formula to calculate distance in meters between two coordinates.
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000; // Earth radius in meters
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // distance in meters
    }
}
