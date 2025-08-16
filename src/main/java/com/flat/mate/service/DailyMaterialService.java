package com.flat.mate.service;

import com.flat.mate.dto.DailyMaterialDTO;
import com.flat.mate.exception.CustomException;
import com.flat.mate.model.DailyMaterial;
import com.flat.mate.model.User;
import com.flat.mate.repository.DailyMaterialRepository;
import com.flat.mate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyMaterialService {

    private final DailyMaterialRepository dailyMaterialRepository;
    private final UserRepository userRepository;

    public DailyMaterialDTO addMaterial(DailyMaterialDTO dto) {
        User addedBy = userRepository.findById(dto.getAddedById())
                .orElseThrow(() -> new CustomException("User not found"));

        List<User> sharedWith = dto.getSharedWithIds().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new CustomException("User not found: " + id)))
                .collect(Collectors.toList());

        DailyMaterial material = new DailyMaterial();
        material.setName(dto.getName());
        material.setCost(dto.getCost());
        material.setAddedBy(addedBy);
        material.setDate(dto.getDate());
        material.setSharedWith(sharedWith);

        DailyMaterial saved = dailyMaterialRepository.save(material);
        return mapToDTO(saved);
    }

    public List<DailyMaterialDTO> getAllMaterials() {
        return dailyMaterialRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private DailyMaterialDTO mapToDTO(DailyMaterial material) {
        List<java.util.UUID> sharedWithIds = material.getSharedWith().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return new DailyMaterialDTO(
                material.getId(),
                material.getName(),
                material.getCost(),
                material.getAddedBy().getId(),
                material.getDate(),
                sharedWithIds
        );
    }
}
