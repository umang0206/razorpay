package com.codingshuttle.razorpay.merchant.dto.request;

import com.codingshuttle.razorpay.common.enums.Environment;
import jakarta.validation.constraints.NotNull;

public record CreateApiKeyRequest (
        @NotNull
        Environment environment){

}
