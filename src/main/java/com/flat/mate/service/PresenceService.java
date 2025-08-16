package com.flat.mate.service;

import com.flat.mate.dto.PresenceDTO;
import com.flat.mate.dto.UserDTO;
import com.flat.mate.exception.CustomException;
import com.flat.mate.model.Presence;
import com.flat.mate.model.User;
import com.flat.mate.repository.PresenceRepository;
import com.flat.mate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresenceService {

    private final PresenceRepository presenceRepository;
    private final UserRepository userRepository;
    @Autowired
    private LocationService locationService;

    public PresenceDTO updatePresenceWithLocation(UserDTO user, double userLat, double userLng) {
        boolean inFlat = locationService.isUserInFlat(userLat, userLng);

        PresenceDTO dto = new PresenceDTO();
        dto.setUserId(user.getId());
        dto.setInFlat(inFlat);

        return updatePresence(dto);
    }


    // 1. Update or create presence record
    public PresenceDTO updatePresence(PresenceDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));

        Presence presence = new Presence();
        presence.setUser(user);
        presence.setInFlat(dto.isInFlat());

        if (dto.isInFlat()) {
            presence.setEntryTime(LocalDateTime.now());
            presence.setExitTime(null); // reset exit time when entering
        } else {
            presence.setExitTime(LocalDateTime.now());
            presence.setEntryTime(dto.getEntryTime()); // keep existing entry
        }

        Presence saved = presenceRepository.save(presence);
        return mapToDTO(saved);
    }

    // 2. Get all users currently in the flat
    public List<PresenceDTO> getUsersCurrentlyInFlat() {
        List<Presence> presences = presenceRepository.findByInFlat(true);
        return presences.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 3. Calculate total hours for a user today
    public double getTotalHoursForUser(User user) {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        List<Presence> presences = presenceRepository.findByUserAndEntryTimeBetween(user, start, end);

        return presences.stream()
                .mapToDouble(Presence::getTotalHours)
                .sum();
    }

    // Mapper from entity to DTO
    private PresenceDTO mapToDTO(Presence presence) {
        return new PresenceDTO(
                presence.getId(),
                presence.getUser().getId(),
                presence.isInFlat(),
                presence.getEntryTime(),
                presence.getExitTime()
        );
    }
}
