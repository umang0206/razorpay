package com.codingshuttle.razorpay.payment.processor.dto;

import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.PaymentMethod;

import java.util.Map;

public record PaymentProcessorRequest(
        PaymentMethod method,
        Money amount,
        String pan,
        String expiry,
        Map<String,Object> methodDetails
) {
}
