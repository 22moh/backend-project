package com.ebdms.backend.dto;

import com.ebdms.backend.enums.BloodType;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateDonorRequest {
    private String fullName;
    private String phone;
    private Double weight;
    private BloodType bloodType;
    private Set<Long> diseaseIds;
    private String preferredGovernorate;
    private String preferredCity;
}