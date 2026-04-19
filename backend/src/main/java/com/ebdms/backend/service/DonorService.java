package com.ebdms.backend.service;

import com.ebdms.backend.dto.UpdateDonorRequest;
import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.model.Disease;
import com.ebdms.backend.model.Donor;
import com.ebdms.backend.repository.DiseaseRepository;
import com.ebdms.backend.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DonorService {
    private final DonorRepository donorRepository;
    private final DiseaseRepository diseaseRepository;



    public List<Donor> getEmergencyDonors(double latitude, double longitude, double radiusInKm, String bloodType) {

        // الداتابيز هترجع المتبرعين المتاحين فقط، ومترتبين من الأقرب للأبعد
        List<Donor> nearestDonors = donorRepository.findNearestDonors(latitude, longitude, radiusInKm, bloodType);

        if (nearestDonors.isEmpty()) {
            throw new RuntimeException("لا يوجد متبرعين متاحين بهذه الفصيلة في النطاق المحدد!");
        }

        return nearestDonors;
    }

    public Page<Donor> getAllDonorsWithFilters(BloodType bloodType, String governorate, int page, int size) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));


        return donorRepository.searchDonors(bloodType, governorate, pageable);
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with ID: " + id));
    }

    // ==========================================
    //   (Update Donor)

    public Donor updateDonor(Long id, UpdateDonorRequest request) {
        // 1. Fetch the existing donor
        Donor existingDonor = getDonorById(id);

        // 2. Update the allowed fields manually
        existingDonor.setFullName(request.getFullName());
        existingDonor.setPhone(request.getPhone());
        existingDonor.setWeight(request.getWeight());
        existingDonor.setBloodType(request.getBloodType());

        existingDonor.setPreferredGovernorate(request.getPreferredGovernorate());
        existingDonor.setPreferredCity(request.getPreferredCity());


        if (request.getDiseaseIds() != null && !request.getDiseaseIds().isEmpty()) {


            Set<Disease> donorDiseases = new HashSet<>(diseaseRepository.findAllById(request.getDiseaseIds()));


            existingDonor.setDiseases(donorDiseases);


            boolean hasPreventiveDisease = donorDiseases.stream()
                    .anyMatch(Disease::isPreventsDonation);


            if (hasPreventiveDisease) {
                existingDonor.setAvailableForDonation(false);
            } else {
                existingDonor.setAvailableForDonation(true);
            }

        } else {

            existingDonor.getDiseases().clear();
            existingDonor.setAvailableForDonation(true);
        }

        // 3. Save and return
        return donorRepository.save(existingDonor);
    }

    // 3. (Block Donor)

    public Donor blockDonor(Long id) {
        Donor donor = getDonorById(id);

        donor.setAvailableForDonation(false);

        return donorRepository.save(donor);
    }
}
