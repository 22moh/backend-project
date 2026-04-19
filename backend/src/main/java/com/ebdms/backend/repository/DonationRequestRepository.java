package com.ebdms.backend.repository;

import com.ebdms.backend.model.DonationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRequestRepository extends JpaRepository<DonationRequest, Long> {

}
