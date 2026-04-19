package com.ebdms.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "diseases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disease_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "prevents_donation")
    @Builder.Default
    private boolean preventsDonation = true;
}
