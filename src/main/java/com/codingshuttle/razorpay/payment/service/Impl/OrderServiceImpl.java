package com.codingshuttle.razorpay.payment.service.Impl;

import com.codingshuttle.razorpay.common.enums.OrderStatus;
import com.codingshuttle.razorpay.common.execptions.BusinessRuleGetViolationException;
import com.codingshuttle.razorpay.common.execptions.DuplicateResourceException;
import com.codingshuttle.razorpay.common.execptions.ResourceNotFoundException;
import com.codingshuttle.razorpay.payment.dto.request.CreateOrderRequest;
import com.codingshuttle.razorpay.payment.dto.response.OrderResponse;
import com.codingshuttle.razorpay.payment.dto.response.PaymentResponse;
import com.codingshuttle.razorpay.payment.entity.OrderRecord;
import com.codingshuttle.razorpay.payment.entity.Payment;
import com.codingshuttle.razorpay.payment.mapper.OrderMapper;
import com.codingshuttle.razorpay.payment.mapper.PaymentMapper;
import com.codingshuttle.razorpay.payment.repository.OrderRepository;
import com.codingshuttle.razorpay.payment.repository.PaymentRepository;
import com.codingshuttle.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;

    @Value("${payment.order.default-order-expiry-minute:30}")
    private int defaultExpiryMinutes;

    @Override
    @Transactional
    public OrderResponse create(UUID merchantId, CreateOrderRequest request) {

        if(request.receipt() != null && orderRepository.existsByMerchantIdAndReceipt(merchantId, request.receipt())) {
            throw new DuplicateResourceException("ORDER_RECEIPT_DUPLICATE", "Order with receipt already exists");
        }

        OrderRecord order = OrderRecord.builder()
                .merchantId(merchantId)
                .amount(request.amount())
                .receipt(request.receipt())
                .orderStatus(OrderStatus.CREATED)
                .notes(request.notes())
                .expriresAt(request.expireAt() != null? request.expireAt() :
                        LocalDateTime.now().plusMinutes(defaultExpiryMinutes))
                .build();
        order = orderRepository.save(order);

        /*return new OrderResponse(order.getId(),order.getMerchantId(),order.getReceipt(),
                order.getAmount(),order.getOrderStatus(),
                order.getAttempts(), order.getNotes(),
                order.getExpriresAt(), null);

         */
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("Order id", orderId));

        /*return new OrderResponse(orderId,merchantId,
                order.getReceipt(),order.getAmount(),
                order.getOrderStatus(),order.getAttempts(),
                order.getNotes(),order.getExpriresAt(),
                null);

         */
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancel(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("Order", orderId));

        if(order.getOrderStatus()== OrderStatus.CANCELLED || order.getOrderStatus()==OrderStatus.PAID){
            throw new BusinessRuleGetViolationException("ORDER_CANNOT_CANCEL", "Order can not cancel with status:" + order.getOrderStatus().name());
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        /*return new OrderResponse(orderId,merchantId,
                order.getReceipt(),order.getAmount(),
                order.getOrderStatus(),order.getAttempts(),
                order.getNotes(),order.getExpriresAt(),
                null);

         */
        return orderMapper.toResponse(order);
    }

    @Override
    public List<PaymentResponse> listPayment(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("Order", orderId));

        List<Payment> paymentList = paymentRepository.findByOrderRecord_Id(orderId);
//        return paymentList.stream().map(
//                payment -> paymentMapper.toResponse(payment)
//        ).collect(Collectors.toList());

        return paymentMapper.toResponseList(paymentList);
    }
}
