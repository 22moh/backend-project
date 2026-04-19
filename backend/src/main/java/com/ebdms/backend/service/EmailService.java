package com.ebdms.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("كود تفعيل حسابك في EBDMS");
        message.setText("أهلاً بك في نظام التبرع بالدم.\n\n" +
                "كود التفعيل الخاص بك هو: " + otp + "\n\n" +
                "برجاء استخدام هذا الكود لإنشاء كلمة المرور الخاصة بك. " +
                "هذا الكود صالح لمدة 10 دقائق فقط.");


        mailSender.send(message);

        System.out.println("تم إرسال الإيميل بنجاح إلى: " + toEmail);
    }
}
