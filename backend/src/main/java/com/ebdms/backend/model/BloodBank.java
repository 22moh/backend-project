/*
package com.ebdms.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blood_banks")
@EntityListeners(AuditingEntityListener.class)
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id") // مطابق للصورة
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "tax_reg_number", nullable = false, unique = true) // مطابق للصورة
    private String taxRegNumber;

    @Column(columnDefinition = "TEXT")
    private String characteristics;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "is_hospital")
    @Builder.Default
    private boolean isHospital = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_admin_id")
    private User addedByAdmin;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
*/
package com.ebdms.backend.model;

import com.ebdms.backend.enums.FacilityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blood_banks")
@EntityListeners(AuditingEntityListener.class) // ده شغلك القديم الممتاز للحفاظ على وقت الإنشاء
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long id;

    // --- 1. Basic Identity ---
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private FacilityType facilityType; // النوع (لازم تعمل الـ Enum بتاعه اللي اتفقنا عليه)

    private String licenseNumber;

    @Column(name = "tax_reg_number", nullable = false, unique = true)
    private String taxRegistrationNumber; // غيرت اسمها لتطابق الشاشات مع الحفاظ على خصائصك

    private String nationalFacilityId;

    // --- 2. Contact Information ---
    private String directorName;
    private String mainPhone;
    private String emergencyPhone;
    private String email;
    private String website;

    // --- 3. Characteristics & Capabilities ---
    private String operatingHours;
    private Integer dailyCapacity;

    // الجداول الفرعية للاختيارات المتعددة (Checkboxes)
    @ElementCollection
    @CollectionTable(name = "blood_bank_services", joinColumns = @JoinColumn(name = "bank_id"))
    private List<String> servicesOffered;

    @ElementCollection
    @CollectionTable(name = "blood_bank_storage", joinColumns = @JoinColumn(name = "bank_id"))
    private List<String> storageCapabilities;

    @ElementCollection
    @CollectionTable(name = "blood_bank_certifications", joinColumns = @JoinColumn(name = "bank_id"))
    private List<String> certifications;

    // --- 4. Location Details ---
    @Column(name = "is_hospital")
    @Builder.Default
    private boolean isHospital = false;

    private String province;
    private String city;
    private String streetName;
    private String buildingNumber;
    //private String googleMapsLink;
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    // --- 5. Additional Notes ---
    @Column(columnDefinition = "TEXT")
    private String additionalNotes;

    // --- 6. Auditing & Relations (شغلك القديم) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_admin_id")
    private Admin addedByAdmin; // رجعتها User زي ما كانت في كودك عشان ماتعملكش إيرور في حتة تانية

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    @Builder.Default
    private boolean isActive = true;
}
