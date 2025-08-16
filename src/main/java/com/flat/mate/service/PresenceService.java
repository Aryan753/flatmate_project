package com.flat.mate.service;

import com.flat.mate.dto.PresenceDTO;
import com.flat.mate.exception.CustomException;
import com.flat.mate.model.Presence;
import com.flat.mate.model.User;
import com.flat.mate.repository.PresenceRepository;
import com.flat.mate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresenceService {

    private final PresenceRepository presenceRepository;
    private final UserRepository userRepository;

    public PresenceDTO updatePresence(PresenceDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));

        Presence presence = new Presence();
        presence.setUser(user);
        presence.setInFlat(dto.isInFlat());
        presence.setEntryTime(dto.getEntryTime());
        presence.setExitTime(dto.getExitTime());

        Presence saved = presenceRepository.save(presence);
        return mapToDTO(saved);
    }

    public List<PresenceDTO> getUsersCurrentlyInFlat() {
        return presenceRepository.findByInFlat(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

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
