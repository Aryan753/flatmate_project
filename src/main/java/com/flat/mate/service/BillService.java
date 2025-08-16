package com.flat.mate.service;

import com.flat.mate.dto.BillDTO;
import com.flat.mate.exception.CustomException;
import com.flat.mate.model.Bill;
import com.flat.mate.model.User;
import com.flat.mate.repository.BillRepository;
import com.flat.mate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final PresenceService presenceService; // inject via constructor

    public BillDTO createBill(BillDTO dto) {
        List<User> participants = dto.getParticipantIds().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new CustomException("User not found: " + id)))
                .collect(Collectors.toList());

        Bill bill = new Bill();
        bill.setType(dto.getType());
        bill.setAmount(dto.getAmount());
        bill.setDate(dto.getDate());
        bill.setParticipants(participants);
        bill.setSplitMethod(dto.getSplitMethod());

        Bill saved = billRepository.save(bill);
        return mapToDTO(saved);
    }

    public List<BillDTO> getAllBills() {
        return billRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BillDTO mapToDTO(Bill bill) {
        List<java.util.UUID> participantIds = bill.getParticipants().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return new BillDTO(
                bill.getId(),
                bill.getType(),
                bill.getAmount(),
                bill.getDate(),
                participantIds,
                bill.getSplitMethod()
        );
    }

    public Map<User, Double> splitBill(double totalBill, List<User> users) {
        Map<User, Double> userShare = new HashMap<>();

        // Calculate total hours of all users
        double totalHours = users.stream()
                .mapToDouble(presenceService::getTotalHoursForUser)
                .sum();

        if (totalHours == 0) totalHours = 1; // prevent division by zero

        for (User user : users) {
            double userHours = presenceService.getTotalHoursForUser(user);
            double share = totalBill * (userHours / totalHours);
            userShare.put(user, share);
        }

        return userShare;
    }
}
