package com.ebdms.backend.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blood_bank_staff")
public class BloodBankStaff {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
 private long id;

@Column(nullable = false)
private String fullName;

private String phone;

@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
private User user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "blood_bank_id", nullable = false)
private BloodBank bloodBank;
}

