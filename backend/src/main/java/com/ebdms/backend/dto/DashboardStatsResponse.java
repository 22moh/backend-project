package com.ebdms.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsResponse {
    private long activeDonors;      // رقم 1: المتبرعين النشطين
    private long monthlyDonations;  // رقم 2: تبرعات الشهر الحالي
    private long bloodStock;        // رقم 3: أكياس الدم المتاحة
}