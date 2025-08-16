package com.flat.mate.repository;

import com.flat.mate.model.DailyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface DailyMaterialRepository extends JpaRepository<DailyMaterial, UUID> {
    List<DailyMaterial> findByDate(LocalDate date);
}
