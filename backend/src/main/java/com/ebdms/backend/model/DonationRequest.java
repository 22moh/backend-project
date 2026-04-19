package com.ebdms.backend.model;

import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.enums.RequestStatus;
import com.ebdms.backend.enums.Severity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donation_requests")
@EntityListeners(AuditingEntityListener.class)
public class DonationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;


    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "patient_age")
    private Integer patientAge;

    @Column(name = "medical_status", columnDefinition = "TEXT")
    private String medicalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    private BloodType bloodType;


    @Column(name = "bags_needed", nullable = false)
    private Integer bagsNeeded;

    @Column(name = "bags_collected")
    private Integer bagsCollected;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false)
    private Severity severity;


    @Column(name = "case_details", columnDefinition = "TEXT")
    private String caseDetails;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "detailed_address", columnDefinition = "TEXT")
    private String detailedAddress;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_bank_id", nullable = false)
    private BloodBank bloodBank;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by_staff_id")
    private CommandCenterStaff assignedByStaff;
}