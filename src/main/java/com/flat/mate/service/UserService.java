package com.flat.mate.service;

import com.flat.mate.dto.UserDTO;
import com.flat.mate.dto.UserRegistrationDTO;
import com.flat.mate.exception.CustomException;
import com.flat.mate.model.User;
import com.flat.mate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO registerUser(UserRegistrationDTO dto) {
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException("Email already registered");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // hash later
        user.setRole(dto.getRole());
        user.setFlatId(dto.getFlatId());

        User saved = userRepository.save(user);
        return UserDTO.mapToDTO(saved);
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with id: " + id));
        return UserDTO.mapToDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::mapToDTO)
                .collect(Collectors.toList());
    }
}
