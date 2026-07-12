package com.codingshuttle.razorpay.payment.processor.dto;

import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.PaymentMethod;

import java.util.Map;
import java.util.UUID;

public record PaymentProcessorRequest(
        UUID processingID,
        UUID paymentId,
        PaymentMethod method,
        Money amount,
        String pan,
        String expiry,
        Map<String,Object> methodDetails
) {

    public static PaymentProcessorRequest card (UUID paymentId, String pan, String expiry,Money amount, Map<String, Object> details){
        return new PaymentProcessorRequest(UUID.randomUUID(),paymentId,PaymentMethod.CARD,amount,pan,expiry,details);
    }

    public static PaymentProcessorRequest nonCard (UUID paymentId,PaymentMethod paymentMethod, Money amount, Map<String, Object> details){
        return new PaymentProcessorRequest(UUID.randomUUID(),paymentId,paymentMethod,amount,null,null,details);
    }
}
