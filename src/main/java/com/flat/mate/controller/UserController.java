package com.flat.mate.controller;

import com.flat.mate.dto.ApiResponse;
import com.flat.mate.dto.UserDTO;
import com.flat.mate.dto.UserRegistrationDTO;
import com.flat.mate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Registration endpoint now uses UserRegistrationDTO
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        UserDTO savedUser = userService.registerUser(registrationDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User fetched successfully", user));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<java.util.List<UserDTO>>> getAllUsers() {
        java.util.List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "All users fetched", users));
    }
}
