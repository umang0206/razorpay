package com.codingshuttle.razorpay.common.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Money {
    private int amountUnit;
    private String currency;

    private Money(int amountUnit, String currency) {
        this.amountUnit = amountUnit;
        this.currency = currency;
    }

    public static Money of(int amountUnit, String currency) {
        return new Money(amountUnit, currency);
    }

    public Money inr(int amountUnit){
        return new Money(amountUnit, "INR");
    }

    public Money add(Money other) {
       if(!this.currency.equals(other.currency)) {
           throw new IllegalArgumentException("Cannot add Money with different currencies");
       }
       return new Money(this.amountUnit + other.amountUnit, this.currency);
    }

    public Money subtract(Money other) {
        if(!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot subtract Money with different currencies");
        }
        return new Money(this.amountUnit - other.amountUnit, this.currency);
    }

}
