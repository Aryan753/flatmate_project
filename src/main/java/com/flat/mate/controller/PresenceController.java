package com.flat.mate.controller;

import com.flat.mate.dto.ApiResponse;
import com.flat.mate.dto.LocationRequest;
import com.flat.mate.dto.PresenceDTO;
import com.flat.mate.dto.UserDTO;
import com.flat.mate.model.User;
import com.flat.mate.service.PresenceService;
import com.flat.mate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/presence")
@RequiredArgsConstructor
public class PresenceController {

    private final PresenceService presenceService;
    private UserService userService;

    @PostMapping("/update-location")
    public ResponseEntity<PresenceDTO> updateLocation(@RequestBody LocationRequest request) {
        UserDTO user = userService.getUserById(request.getUserId());
        PresenceDTO updated = presenceService.updatePresenceWithLocation(user, request.getLatitude(), request.getLongitude());
        return ResponseEntity.ok(updated);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<PresenceDTO>> updatePresence(@RequestBody PresenceDTO presenceDTO) {
        PresenceDTO updated = presenceService.updatePresence(presenceDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Presence updated", updated));
    }

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<List<PresenceDTO>>> getUsersInFlat() {
        List<PresenceDTO> inFlat = presenceService.getUsersCurrentlyInFlat();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users currently in flat fetched", inFlat));
    }
}
