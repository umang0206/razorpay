package com.codingshuttle.razorpay.common.enums;

public enum PaymentEvent {
    AUTHORIZE_ATTEMPT,
    AUTHORIZE_SUCCESS,
    AUTHORIZE_FAIL,
    CAPTURE_REQUEST,
    CAPTURE_SUCCESS,
    CAPTURE_FAIL,
    REFUND_INITIATE,
    REFUND_COMPLETE,
    REFUNDED_FAILED,
    SETTLED,
    CANCEL,
    CAPTURE_TIMEOUT,
}
