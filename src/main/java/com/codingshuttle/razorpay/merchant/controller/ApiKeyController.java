package com.codingshuttle.razorpay.merchant.controller;

import com.codingshuttle.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyResponse;
import com.codingshuttle.razorpay.merchant.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchant/{merchant_id}/api-key")
@RequiredArgsConstructor
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    @PostMapping()
    public ResponseEntity<ApiKeyCreateResponse> create(@PathVariable("merchant_id") UUID merchantId,
                                                       @RequestBody @Valid CreateApiKeyRequest request) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(apiKeyService.create(merchantId, request));
    }

    @GetMapping
    public ResponseEntity<List<ApiKeyResponse>> getAllApiKey(@PathVariable("merchant_id") UUID merchantId) {
        return ResponseEntity.ok(apiKeyService.listByMerchant(merchantId));

    }

    @DeleteMapping("/{keyId}")
    public ResponseEntity<Void> revoke(@PathVariable("merchant_id") UUID merchantId, @PathVariable("keyId") UUID id){
        apiKeyService.revoke(merchantId,id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{keyId}/rotate")
    public ResponseEntity<ApiKeyCreateResponse> rotate(@PathVariable("merchant_id") UUID merchantId, @PathVariable UUID keyId) {
        return ResponseEntity.ok(apiKeyService.rotateKey(merchantId,keyId));
    }
}
