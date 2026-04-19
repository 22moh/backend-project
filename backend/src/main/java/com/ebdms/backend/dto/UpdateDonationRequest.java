package com.ebdms.backend.dto;


import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.enums.Severity;
import lombok.Data;

@Data
public class UpdateDonationRequest {
    private String patientName;
    private Integer patientAge;
    private String medicalStatus;
    private BloodType bloodType;
    private Integer bagsNeeded;
    private Severity severity;
    private String caseDetails;
    private String city;
    private String detailedAddress;
    private String notes;
}