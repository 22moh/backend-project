/*
package com.ebdms.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddBloodBankRequest {
    private String name;
    private String address;
    private String taxRegNumber;
    private String characteristics;
    private String contactPhone;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private boolean isHospital;

    // هناخد الـ ID بتاع الآدمن اللي بيضيف البنك عشان التوثيق
    private Long addedByAdminId;
}
*/
package com.ebdms.backend.dto;

import com.ebdms.backend.enums.FacilityType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddBloodBankRequest {

    // --- 1. Basic Identity ---
    private String name;
    private FacilityType facilityType;
    private String licenseNumber;
    private String taxRegistrationNumber;
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

    private List<String> servicesOffered;
    private List<String> storageCapabilities;
    private List<String> certifications;

    // --- 4. Location Details ---
    private boolean isHospital;
    private String province;
    private String city;
    private String streetName;
    private String buildingNumber;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    // --- 5. Additional Notes ---
    private String additionalNotes;

    // --- 6. Admin ID ---
    // TODO: Remove this field after implementing Spring Security & JWT
    private Long addedByAdminId;
}