package com.codingshuttle.razorpay.common.execptions;

import lombok.Getter;

@Getter
public class InvalidStateTransitionException extends RuntimeException {
    private final String fromState;
    private final  String toEvent;

    public InvalidStateTransitionException(String fromCurrent,  String toEvent) {
        super("Invalid Transition from: " + fromCurrent + " with event: " + toEvent );
        this.fromState = fromCurrent;
        this.toEvent = toEvent;
    }
}
