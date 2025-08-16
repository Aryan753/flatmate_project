package com.flat.mate.controller;

import com.flat.mate.dto.ApiResponse;
import com.flat.mate.dto.BillDTO;
import com.flat.mate.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    public ResponseEntity<ApiResponse<BillDTO>> addBill(@RequestBody BillDTO billDTO) {
        BillDTO createdBill = billService.createBill(billDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Bill created", createdBill));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BillDTO>>> getAllBills() {
        List<BillDTO> bills = billService.getAllBills();
        return ResponseEntity.ok(new ApiResponse<>(true, "All bills fetched", bills));
    }
}
