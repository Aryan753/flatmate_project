package com.flat.mate.repository;

import com.flat.mate.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findByDate(LocalDate date);
}
