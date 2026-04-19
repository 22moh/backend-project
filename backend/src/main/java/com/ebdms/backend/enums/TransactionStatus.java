package com.ebdms.backend.enums;

public enum TransactionStatus {
    // 1. مرحلة الترشيح (Allocation Phase)
    SUGGESTED,           // النظام اقترح المتبرع ده تلقائياً
    MANUALLY_ADDED,      // موظف المركز أضاف المتبرع يدوياً

    // 2. مرحلة الإشعار (Notification Phase)
    NOTIFIED,            // تم إرسال الإشعار للمتبرع بنجاح
    NOTIFICATION_FAILED, // فشل إرسال الإشعار (موبايله مقفول أو مشكلة نت)

    // 3. رد المتبرع (Donor Response)
    DONOR_ACCEPTED,      // المتبرع وافق مبدئياً على التطبيق
    DONOR_REJECTED,      // المتبرع رفض الطلب
    TIMEOUT,             // المتبرع ماردش خلال الوقت المحدد

    // 4. تأكيد المركز (Confirmation)
    APPROVED_TO_GO,      // المركز أعطى الضوء الأخضر للمتبرع بالتحرك

    // 5. التنفيذ (Execution Phase)
    ON_WAY,              // المتبرع ضغط زر "أنا في الطريق"
    ARRIVED,             // المتبرع وصل المستشفى (GPS أو تأكيد وصول)
    SAMPLE_COLLECTED,    // تم سحب العينة للتحليل
    COMPLETED,           // تمت عملية التبرع بنجاح

    // 6. الاستثناءات والإلغاء (Exceptions)
    MEDICAL_REJECTION,   // تم الرفض داخل المستشفى لأسباب طبية (ضغط، انيميا...)
    CANCELLED_BY_CENTER
}

