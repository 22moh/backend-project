package com.ebdms.backend.repository;

import com.ebdms.backend.enums.BloodType;
import com.ebdms.backend.model.Donor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    @Query("SELECT d FROM Donor d WHERE " +
            "(:bloodType IS NULL OR d.bloodType = :bloodType) AND " +
            "(:governorate IS NULL OR d.preferredGovernorate = :governorate)")
    Page<Donor> searchDonors(
            @Param("bloodType") BloodType bloodType,
            @Param("governorate") String governorate,
            Pageable pageable
    );
    // جلب أقرب المتبرعين المتاحين بناءً على المسافة والفصيلة
    @Query(value = """
        SELECT * FROM donors d 
        WHERE d.is_available_for_donation = true 
        AND d.blood_type = :bloodType 
        AND (6371 * acos(cos(radians(:latitude)) * cos(radians(d.latitude)) 
        * cos(radians(d.longitude) - radians(:longitude)) 
        + sin(radians(:latitude)) * sin(radians(d.latitude)))) <= :radius 
        ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(d.latitude)) 
        * cos(radians(d.longitude) - radians(:longitude)) 
        + sin(radians(:latitude)) * sin(radians(d.latitude)))) ASC
        """, nativeQuery = true)
    List<Donor> findNearestDonors(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("radius") double radius,
            @Param("bloodType") String bloodType
    );
}