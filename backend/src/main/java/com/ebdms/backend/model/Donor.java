package com.ebdms.backend.model;

import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.enums.DonorRegistrationStatus;
import com.ebdms.backend.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donors")
@EntityListeners(AuditingEntityListener.class)
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Column(name = "national_id", nullable = false, unique = true)
    private String nationalId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    private BloodType bloodType;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
/*
    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Column(name = "medical_info", columnDefinition = "TEXT")
    private String medicalInfo;*/
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
        name = "donor_diseases",
        joinColumns = @JoinColumn(name = "donor_id"),
        inverseJoinColumns = @JoinColumn(name = "disease_id")
)
 private Set<Disease> diseases = new HashSet<>();

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "current_latitude", precision = 10, scale = 8)
    private BigDecimal currentLatitude;

    @Column(name = "current_longitude", precision = 11, scale = 8)
    private BigDecimal currentLongitude;


    @Column(name = "preferred_governorate")
    private String preferredGovernorate;

    @Column(name = "preferred_city")
    private String preferredCity;

    @Column(name = "is_available_for_donation")
    @Builder.Default
    private boolean isAvailableForDonation = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status")
    @Builder.Default
    private DonorRegistrationStatus registrationStatus = DonorRegistrationStatus.APPROVED;

    @Column(name = "next_eligible_date")
    private LocalDate nextEligibleDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}