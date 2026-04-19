package com.ebdms.backend.controller;

import com.ebdms.backend.dto.UpdateDonorRequest;
import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.model.Donor;
import com.ebdms.backend.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;


    @GetMapping("/emergency-dispatch")
    public ResponseEntity<?> dispatchEmergencyDonors(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radius,
            @RequestParam String bloodType
    ) {
        try {

            List<Donor> donors = donorService.getEmergencyDonors(lat, lng, radius, bloodType);
            return ResponseEntity.ok(donors);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    // استعراض المتبرعين للأغراض الإدارية والفلترة العادية
    @GetMapping("/registry")
    public ResponseEntity<?> browseDonorRegistry(
            @RequestParam(required = false) BloodType bloodType,
            @RequestParam(required = false) String governorate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {

            Page<Donor> donorsPage = donorService.getAllDonorsWithFilters(bloodType, governorate, page, size);


            return ResponseEntity.ok(donorsPage);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}/update-medical-record")
    public ResponseEntity<?> updateDonor(
            @PathVariable Long id,
            @RequestBody UpdateDonorRequest request) {
        try {
            Donor updatedDonor = donorService.updateDonor(id, request);
            return ResponseEntity.ok(updatedDonor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<?> blockDonor(@PathVariable Long id) {
        try {
            Donor blockedDonor = donorService.blockDonor(id);
            return ResponseEntity.ok(blockedDonor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}