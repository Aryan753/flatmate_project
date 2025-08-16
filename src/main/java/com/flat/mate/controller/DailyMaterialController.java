package com.flat.mate.controller;

import com.flat.mate.dto.ApiResponse;
import com.flat.mate.dto.DailyMaterialDTO;
import com.flat.mate.service.DailyMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class DailyMaterialController {

    private final DailyMaterialService dailyMaterialService;

    @PostMapping
    public ResponseEntity<ApiResponse<DailyMaterialDTO>> addMaterial(@RequestBody DailyMaterialDTO materialDTO) {
        DailyMaterialDTO savedMaterial = dailyMaterialService.addMaterial(materialDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Material added", savedMaterial));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DailyMaterialDTO>>> getAllMaterials() {
        List<DailyMaterialDTO> materials = dailyMaterialService.getAllMaterials();
        return ResponseEntity.ok(new ApiResponse<>(true, "All materials fetched", materials));
    }
}
