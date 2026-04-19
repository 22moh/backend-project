package com.ebdms.backend.service;

import com.ebdms.backend.dto.DonorRegistrationRequest;
import com.ebdms.backend.enums.DonorRegistrationStatus;
import com.ebdms.backend.enums.Role;
import com.ebdms.backend.model.*;
import com.ebdms.backend.repository.DonorRepository;
import com.ebdms.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final DonorRepository donorRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // =================================================================
    // 1. دالة تسجيل الدخول (Login) - لكل الأنواع (Admin, Bank, Donor)
    // =================================================================
    public User login(String email, String password) {
        // 1. البحث عن المستخدم بالإيميل
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or user not found"));

        // 2. التحقق من الباسورد (مقارنة مباشرة حالياً بدون تشفير)
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // 3. التحقق إن الحساب مش محظور أو غير مفعل
        if (user.isBlocked()) {
            throw new RuntimeException("Account is BLOCKED by Admin");
        }
        if (!user.isActive()) {
            throw new RuntimeException("Account is INACTIVE");
        }

        // 4. كله تمام -> رجع اليوزر
        return user;
    }

    // =================================================================
    // 2. دالة تسجيل متبرع جديد (Registration) - للمتبرع فقط
    // =================================================================
    @Transactional
    public User registerDonor(DonorRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // 1. Create User
        User newUser = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.DONOR)
                .isActive(true)
                .isBlocked(false)
                .mustChangePassword(false)
                .build();

        // 2. Create Donor (Updated Fields)
        Donor newDonor = Donor.builder()
                .user(newUser)
                .fullName(request.getFullName())
                .nationalId(request.getNationalId())
                .phone(request.getPhone())
                .weight(request.getWeight())
                .birthDate(request.getBirthDate())
                .bloodType(request.getBloodType())
                .gender(request.getGender())
                // الموقع (لو مش مبعوت هيكون null وده عادي)
                .preferredGovernorate(request.getPreferredGovernorate())
                .preferredCity(request.getPreferredCity())
                .currentLatitude(request.getCurrentLatitude())
                .currentLongitude(request.getCurrentLongitude())
                .registrationStatus(DonorRegistrationStatus.PENDING)
                .isAvailableForDonation(true)
                .build();

        donorRepository.save(newDonor);

        return newUser;
    }

    public String generateAndSendOtp(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("هذا الإيميل غير مسجل في النظام");
        }

        User user = optionalUser.get();


        String otp = String.format("%06d", new Random().nextInt(999999));


        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);


        user.setOtp(otp);
        user.setOtpExpiryTime(expiryTime);
        userRepository.save(user);


        emailService.sendOtpEmail(user.getEmail(), otp);

        return "تم إرسال كود التفعيل إلى إيميلك بنجاح";
    }
    public String verifyOtpAndSetPassword(String email, String otp, String newPassword){
        User user =userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("المستخدم غير موجود"));

        if(user.getOtp()==null||!user.getOtp().equals(otp)){
            throw new RuntimeException("كود التفعيل غير صحيح");
        }

        if(user.getOtpExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("انتهت صلاحية الكود، برجاء طلب كود جديد");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setActive(true);
        user.setMustChangePassword(false);

        user.setOtp(null);
        user.setOtpExpiryTime(null);

        userRepository.save(user);
        return "تم تفعيل الحساب وتغيير كلمة المرور بنجاح!";
    }


}