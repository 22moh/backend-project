/*
package com.ebdms.backend.service;

import com.ebdms.backend.dto.AddBloodBankRequest;
import com.ebdms.backend.model.BloodBank;
import com.ebdms.backend.model.User;
import com.ebdms.backend.repository.BloodBankRepository;
import com.ebdms.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;
    private final UserRepository userRepository;

    @Transactional
    public BloodBank addBloodBank(AddBloodBankRequest request) {
        // 1. نتأكد إن رقم التسجيل الضريبي مش متكرر
        // (ممكن نضيف دالة في الـ Repository: existsByTaxRegNumber)
        // بس للتسهيل دلوقتي هنكمل.

        // 2. نجيب الآدمن اللي بيضيف البنك (عشان نسجله في added_by)
        User adminUser = userRepository.findById(request.getAddedByAdminId())
                .orElseThrow(() -> new RuntimeException("Admin User not found"));

        // 3. نجهز كيان البنك (Mapping)
        BloodBank newBank = BloodBank.builder()
                .name(request.getName())
                .address(request.getAddress())
                .taxRegNumber(request.getTaxRegNumber())
                .characteristics(request.getCharacteristics())
                .contactPhone(request.getContactPhone())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .isHospital(request.isHospital())
                .addedByAdmin(adminUser) // هنا ربطنا البنك بالآدمن
                .build();

        // 4. الحفظ في الداتابيز
        return bloodBankRepository.save(newBank);
    }
}*/
package com.ebdms.backend.service;

import com.ebdms.backend.dto.AddBloodBankRequest;
import com.ebdms.backend.dto.UpdateBloodBankRequest;
import com.ebdms.backend.model.Admin;
import com.ebdms.backend.model.BloodBank;
import com.ebdms.backend.model.User;
import com.ebdms.backend.repository.AdminRepository;
import com.ebdms.backend.repository.BloodBankRepository;
import com.ebdms.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public BloodBank addBloodBank(AddBloodBankRequest request) {

        // 1. Validation (التفتيش الأمني): نتأكد إن رقم التسجيل الضريبي مش متكرر
        if (bloodBankRepository.existsByTaxRegistrationNumber(request.getTaxRegistrationNumber())) {
            throw new RuntimeException("Tax Registration Number already exists!");
        }

        // 2. نجيب الآدمن اللي بيضيف البنك (عشان نسجله في added_by)
        Admin adminUser = adminRepository.findById(request.getAddedByAdminId())
                .orElseThrow(() -> new RuntimeException("Admin User not found"));


        BloodBank newBank = BloodBank.builder()
                // --- 1. Basic Identity ---
                .name(request.getName())
                .facilityType(request.getFacilityType())
                .licenseNumber(request.getLicenseNumber())
                .taxRegistrationNumber(request.getTaxRegistrationNumber())
                .nationalFacilityId(request.getNationalFacilityId())

                // --- 2. Contact Information ---
                .directorName(request.getDirectorName())
                .mainPhone(request.getMainPhone())
                .emergencyPhone(request.getEmergencyPhone())
                .email(request.getEmail())
                .website(request.getWebsite())

                // --- 3. Characteristics & Capabilities ---
                .operatingHours(request.getOperatingHours())
                .dailyCapacity(request.getDailyCapacity())
                .servicesOffered(request.getServicesOffered())         // لستة الخدمات
                .storageCapabilities(request.getStorageCapabilities()) // لستة التخزين
                .certifications(request.getCertifications())           // لستة الشهادات

                // --- 4. Location Details ---
                .isHospital(request.isHospital())
                .province(request.getProvince())
                .city(request.getCity())
                .streetName(request.getStreetName())
                .buildingNumber(request.getBuildingNumber())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())


                // --- 5. Additional Notes ---
                .additionalNotes(request.getAdditionalNotes())

                // --- 6. Relations ---
                .addedByAdmin(adminUser)
                .isActive(true)// ربطنا البنك بالآدمن اللي ضافه
                .build();

        // 4. الحفظ في الداتابيز
        return bloodBankRepository.save(newBank);
    }

    public List <BloodBank> getAllBloodBanks(){
       return bloodBankRepository.findAll();

    }
    public BloodBank getBloodBankById(Long id) {
        return bloodBankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood Banks not exisit with " + id));
    }

    @Transactional
    public BloodBank updateBloodBank(Long id, UpdateBloodBankRequest request) {


        BloodBank existingBank = getBloodBankById(id);


        // --- 1. Basic Identity ---
        existingBank.setName(request.getName());
        existingBank.setFacilityType(request.getFacilityType());
        existingBank.setLicenseNumber(request.getLicenseNumber());
        existingBank.setNationalFacilityId(request.getNationalFacilityId());

        // --- 2. Contact Information ---
        existingBank.setDirectorName(request.getDirectorName());
        existingBank.setMainPhone(request.getMainPhone());
        existingBank.setEmergencyPhone(request.getEmergencyPhone());
        existingBank.setEmail(request.getEmail());
        existingBank.setWebsite(request.getWebsite());

        // --- 3. Characteristics & Capabilities ---
        existingBank.setOperatingHours(request.getOperatingHours());
        existingBank.setDailyCapacity(request.getDailyCapacity());
        existingBank.setServicesOffered(request.getServicesOffered());
        existingBank.setStorageCapabilities(request.getStorageCapabilities());
        existingBank.setCertifications(request.getCertifications());

        // --- 4. Location Details ---
        existingBank.setHospital(request.isHospital());
        existingBank.setProvince(request.getProvince());
        existingBank.setCity(request.getCity());
        existingBank.setStreetName(request.getStreetName());
        existingBank.setBuildingNumber(request.getBuildingNumber());
        existingBank.setAddress(request.getAddress());
        existingBank.setLatitude(request.getLatitude());
        existingBank.setLongitude(request.getLongitude());


        // --- 5. Additional Notes ---
        existingBank.setAdditionalNotes(request.getAdditionalNotes());

        // . نحفظ الملف بعد التعديل
        return bloodBankRepository.save(existingBank);
    }
    @Transactional
    public BloodBank blockBloodBank(Long id) {
        BloodBank bloodBank=getBloodBankById( id);
        bloodBank.setActive(false);

       return bloodBankRepository.save(bloodBank);
    }




}