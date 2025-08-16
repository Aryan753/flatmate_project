package com.flat.mate.controller;

import com.flat.mate.dto.ApiResponse;
import com.flat.mate.dto.PresenceDTO;
import com.flat.mate.service.PresenceService;
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
