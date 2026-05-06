package com.ebdms.backend.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. استخراج الـ Header اللي المفروض يكون فيه التوكن
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. لو مفيش Header أو مش بيبدأ بكلمة Bearer، عدي الطلب (ممكن يكون بيعمل Login أو بيبعت OTP)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. استخراج التوكن (بنشيل أول 7 حروف اللي هما "Bearer ")
        jwt = authHeader.substring(7);

        // 4. استخراج الإيميل من التوكن (بواسطة المكنة بتاعتنا JwtService)
        userEmail = jwtService.extractUsername(jwt);

        // 5. لو الإيميل موجود واليوزر مش متسجل في الـ Security Context (يعني لسه متعملوش Authentication في الطلب ده)
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // بنجيب بيانات اليوزر من الداتابيز
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. بنتأكد إن التوكن سليم وبتاع اليوزر ده
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 7. لو سليم، بنعمل كارت مرور رسمي (AuthenticationToken)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // بنضيف شوية تفاصيل من الريكويست (زي الـ IP address)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 8. بندي كارت المرور ده للـ Security Context (كده السيرفر عرف إن اليوزر ده معتمد)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 9. بنكمل السلسلة ونودي الريكويست للـ Controller
        filterChain.doFilter(request, response);
    }
}