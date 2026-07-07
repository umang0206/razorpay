package com.codingshuttle.razorpay.common.execptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final Object identifier;


    public ResourceNotFoundException(String resource, Object identifier) {
        super(resource + " not found: " + identifier);
        this.resource = resource;
        this.identifier = identifier;
    }
}
