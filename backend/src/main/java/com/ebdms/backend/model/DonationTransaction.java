package com.ebdms.backend.model;

import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.enums.TransactionStatus;
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
@Table(name = "donation_transactions")
@EntityListeners(AuditingEntityListener.class)
public class DonationTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private DonationRequest request;


    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_user_id", nullable = false)
    private User donorUser;
*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private TransactionStatus status = TransactionStatus.SUGGESTED;



    @Column(name = "estimated_arrival_time")
    private LocalDateTime estimatedArrivalTime;

    @Column(name = "amount_collected_ml")
    private Integer amountCollectedMl;

    @Enumerated(EnumType.STRING)
    @Column(name = "verified_blood_type")
    private BloodType verifiedBloodType;

    @Column(name = "medical_report", columnDefinition = "TEXT")
    private String medicalReport;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;



    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}