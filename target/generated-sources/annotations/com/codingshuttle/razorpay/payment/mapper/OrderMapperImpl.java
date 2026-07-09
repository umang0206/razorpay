package com.codingshuttle.razorpay.payment.mapper;

import com.codingshuttle.razorpay.common.entity.Money;
import com.codingshuttle.razorpay.common.enums.OrderStatus;
import com.codingshuttle.razorpay.payment.dto.response.OrderResponse;
import com.codingshuttle.razorpay.payment.entity.OrderRecord;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T12:04:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponse toResponse(OrderRecord orderRecord) {
        if ( orderRecord == null ) {
            return null;
        }

        UUID id = null;
        UUID merchantId = null;
        String receipt = null;
        Money amount = null;
        Integer attempts = null;
        Map<String, Object> notes = null;

        id = orderRecord.getId();
        merchantId = orderRecord.getMerchantId();
        receipt = orderRecord.getReceipt();
        amount = orderRecord.getAmount();
        attempts = orderRecord.getAttempts();
        Map<String, Object> map = orderRecord.getNotes();
        if ( map != null ) {
            notes = new LinkedHashMap<String, Object>( map );
        }

        OrderStatus status = null;
        LocalDateTime expiresAt = null;
        LocalDateTime createdAt = null;

        OrderResponse orderResponse = new OrderResponse( id, merchantId, receipt, amount, status, attempts, notes, expiresAt, createdAt );

        return orderResponse;
    }
}
