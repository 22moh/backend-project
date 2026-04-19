package com.ebdms.backend.dto;

import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.enums.Gender;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class DonorRegistrationRequest {
    // بيانات User
    private String email;
    private String password;

    // بيانات Donor
    private String nationalId;
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
    private BloodType bloodType; // Optional
    private Double weight;
    private String phone;

    // بيانات الموقع
    private String preferredGovernorate;
    private String preferredCity;
    private BigDecimal currentLatitude;
    private BigDecimal currentLongitude;
}
