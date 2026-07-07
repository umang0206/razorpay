package com.codingshuttle.razorpay.merchant.dto.request;

import com.codingshuttle.razorpay.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchantSignupRequest(

        @NotNull(message = "Name is required")
        @Size(max= 50, message = "Name can not be more than 50 characters")
        String name,

        @Email
        @NotNull(message = "Email is required")
        String email,

        @NotNull(message = "password is required")
        @Size(min = 8, message = "Password should be minimum 8 characters")
        String password,

        @Size(max = 50, message = "Business name can not be more than 50 characters")
        String businessName,

        BusinessType businessType
) {
}
