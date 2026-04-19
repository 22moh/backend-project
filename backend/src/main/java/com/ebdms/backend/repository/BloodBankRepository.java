package com.ebdms.backend.repository;

import com.ebdms.backend.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    boolean existsByTaxRegistrationNumber(String taxRegistrationNumber);
}
