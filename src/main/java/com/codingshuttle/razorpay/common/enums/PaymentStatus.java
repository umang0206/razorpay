package com.codingshuttle.razorpay.common.enums;

public enum PaymentStatus {
    CREATED,
    AUTHORIZING,
    AUTHORIZED,
    CAPTURING,
    CAPTURED,
    REFUNDED,
    FAILED,
    SETTLED,
    PARTIALLY_REFUNDED,
    AUTH_EXPIRED,
}
