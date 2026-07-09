package com.codingshuttle.razorpay.common.execptions;

import lombok.Getter;

@Getter
public class BusinessRuleGetViolationException extends RuntimeException {
    private final String errorCode;

    public BusinessRuleGetViolationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
