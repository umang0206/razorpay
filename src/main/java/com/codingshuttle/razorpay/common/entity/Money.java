package com.codingshuttle.razorpay.common.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Money {
    private int amountUnits;
    private String currency;

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
       return new Money(this.amountUnits + other.amountUnits, this.currency);
    }

    public Money subtract(Money other) {
        if(!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot subtract Money with different currencies");
        }
        return new Money(this.amountUnits - other.amountUnits, this.currency);
    }

}
