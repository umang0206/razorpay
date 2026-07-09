package com.codingshuttle.razorpay.payment.mapper;

import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.PaymentMethod;
import com.codingshuttle.razorpay.common.enums.PaymentStatus;
import com.codingshuttle.razorpay.payment.dto.response.PaymentResponse;
import com.codingshuttle.razorpay.payment.entity.OrderRecord;
import com.codingshuttle.razorpay.payment.entity.Payment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T11:26:40+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        UUID orderId = null;
        UUID id = null;
        UUID merchantId = null;
        Map<String, Object> methodDetails = null;
        String bankReference = null;
        String errorCode = null;
        String errorDescription = null;
        LocalDateTime capturedAt = null;

        orderId = paymentOrderRecordId( payment );
        id = payment.getId();
        merchantId = payment.getMerchantId();
        Map<String, Object> map = payment.getMethodDetails();
        if ( map != null ) {
            methodDetails = new LinkedHashMap<String, Object>( map );
        }
        bankReference = payment.getBankReference();
        errorCode = payment.getErrorCode();
        errorDescription = payment.getErrorDescription();
        capturedAt = payment.getCapturedAt();

        Money amount = null;
        PaymentStatus paymentStatus = null;
        PaymentMethod paymentMethod = null;
        String cardLastFour = null;
        String cardBrand = null;
        Long refundedAmountPaise = null;
        LocalDateTime createdAt = null;

        PaymentResponse paymentResponse = new PaymentResponse( id, orderId, merchantId, amount, paymentStatus, paymentMethod, methodDetails, cardLastFour, cardBrand, bankReference, errorCode, errorDescription, refundedAmountPaise, capturedAt, createdAt );

        return paymentResponse;
    }

    @Override
    public List<PaymentResponse> toResponseList(List<Payment> paymentList) {
        if ( paymentList == null ) {
            return null;
        }

        List<PaymentResponse> list = new ArrayList<PaymentResponse>( paymentList.size() );
        for ( Payment payment : paymentList ) {
            list.add( toResponse( payment ) );
        }

        return list;
    }

    private UUID paymentOrderRecordId(Payment payment) {
        OrderRecord orderRecord = payment.getOrderRecord();
        if ( orderRecord == null ) {
            return null;
        }
        return orderRecord.getId();
    }
}
