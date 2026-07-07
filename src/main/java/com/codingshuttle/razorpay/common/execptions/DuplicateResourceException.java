package com.codingshuttle.razorpay.common.execptions;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {
    private final String errorCode;

    public DuplicateResourceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
