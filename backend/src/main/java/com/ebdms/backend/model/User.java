package com.ebdms.backend.model;

import com.ebdms.backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    @Builder.Default
    private boolean mustChangePassword = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean isBlocked = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean isActive = false;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expiry_time")
    private LocalDateTime otpExpiryTime;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ========================================================================
    // Methods required by Spring Security (UserDetails Interface)
    // ========================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // بنحول الـ Enum بتاعك لصلاحية السبرينج بيفهمها (بنزود كلمة ROLE_ كعُرف برمجي)
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        // السبرينج بيعتبر الـ Username هو المعرف الأساسي، في حالتنا هو الإيميل
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // الحساب مبينتهيش
    }

    @Override
    public boolean isAccountNonLocked() {
        // ربط عبقري: لو الحساب isBlocked بـ true، السبرينج هيقفل عليه الدخول أوتوماتيك!
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // الباسورد مبتبوظش
    }

    @Override
    public boolean isEnabled() {
        // ربط عبقري 2: لو الحساب isActive بـ false (لسه مفعلش الـ OTP)، مش هيعرف يعمل Login
        return isActive;
    }
}