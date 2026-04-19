package com.ebdms.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// دي أهم سطر: بتقول لـ Spring لو الخطأ ده حصل، رجع كود 404
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // الكونستركتور الأول: بياخد رسالة عادية
    // مثال: throw new ResourceNotFoundException("User not found");
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // الكونستركتور التاني (احترافي): بيفصل الرسالة
    // مثال: throw new ResourceNotFoundException("User", "id", 5);
    // النتيجة: "User not found with id : '5'"
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
