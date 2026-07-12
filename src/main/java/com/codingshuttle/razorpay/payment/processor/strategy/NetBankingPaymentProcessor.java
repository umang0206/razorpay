package com.codingshuttle.razorpay.payment.processor.strategy;

import com.codingshuttle.razorpay.common.util.RandomizerUtil;
import com.codingshuttle.razorpay.payment.processor.PaymentProcessor;
import com.codingshuttle.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.codingshuttle.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class NetBankingPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        final String BANK_CODE_FAILED = "BANK_CODE_FAILED";

        String bankCode = request.methodDetails()!= null ?
                request.methodDetails().get("BANK").toString() : null;

        //simulation
        if(BANK_CODE_FAILED.equals(bankCode)){
            return new PaymentProcessorResponse.Failure
                    ("BANK_REJECTED", "Banked rejected the transaction registration");
        }

        String processorRef = "NBK_PROCESSOR_" + RandomizerUtil.randomBase64(16);
        String redirectRef = "http://REDIRECTED_BANK.com/"+processorRef;

        return new PaymentProcessorResponse.Success(processorRef, redirectRef);
    }
}
