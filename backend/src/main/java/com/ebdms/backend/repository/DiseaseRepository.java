package com.ebdms.backend.repository;

import com.ebdms.backend.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    Optional<Disease> findByName(String name);
}
