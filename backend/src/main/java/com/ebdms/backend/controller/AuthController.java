package com.ebdms.backend.controller;

import com.ebdms.backend.dto.DonorRegistrationRequest;
import com.ebdms.backend.dto.LoginRequest;
import com.ebdms.backend.model.User;
import com.ebdms.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // السماح للفرونت إند بالوصول
public class AuthController {

    private final AuthService authService;

    // 1. رابط تسجيل الدخول
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // 2. رابط تسجيل متبرع جديد
    @PostMapping("/register-donor")
    public ResponseEntity<?> registerDonor(@RequestBody DonorRegistrationRequest request) {
        try {
            User createdUser = authService.registerDonor(request);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        try {
            String result = authService.generateAndSendOtp(email);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email,
                                            @RequestParam String otp,
                                            @RequestParam String newPassword){
        try {
            String message = authService.verifyOtpAndSetPassword(email, otp, newPassword);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}