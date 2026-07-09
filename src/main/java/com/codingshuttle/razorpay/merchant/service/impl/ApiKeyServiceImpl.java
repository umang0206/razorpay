package com.codingshuttle.razorpay.merchant.service.impl;

import com.codingshuttle.razorpay.common.execptions.ResourceNotFoundException;
import com.codingshuttle.razorpay.common.util.RandomizerUtil;
import com.codingshuttle.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyResponse;
import com.codingshuttle.razorpay.merchant.entity.ApiKey;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import com.codingshuttle.razorpay.merchant.mapper.ApiKeyMapper;
import com.codingshuttle.razorpay.merchant.repository.ApiKeyRepository;
import com.codingshuttle.razorpay.merchant.repository.MerchantRepository;
import com.codingshuttle.razorpay.merchant.service.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyMapper apiKeyMapper;

    @Override
    @Transactional
    public ApiKeyCreateResponse create(UUID merchantid, CreateApiKeyRequest request) {
        Merchant merchant = merchantRepository.findById(merchantid)
                .orElseThrow(() -> new ResourceNotFoundException("merchant", merchantid));

        String keyId = "rzp_"+ request.environment().name().toLowerCase()+"_"+ RandomizerUtil.randomBase64(24);
        String rawSecret = RandomizerUtil.randomBase64(40);

        ApiKey apiKey = ApiKey.builder()
                .keyID(keyId)
                .keySecretHash(rawSecret)
                .merchant(merchant)
                .environment(request.environment())
                .enabled(true)
                .build();

        apiKey = apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(), keyId, rawSecret, request.environment());
    }

    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {

        /*return apiKeyRepository.findByMerchant_Id(merchantId)
                .stream()
                .map(apiKey ->
                        new ApiKeyResponse (
                                apiKey.getId(),
                                apiKey.getKeyID(),
                                apiKey.getEnvironment(),
                                apiKey.getEnabled(),
                                apiKey.getLastUsedAt(),
                                null))

                .toList();
         */
        return apiKeyMapper.toApiKeyResponseList(apiKeyRepository.findByMerchant_Id(merchantId));
    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID id) {
        ApiKey apiKey = apiKeyRepository.findById(id)
                .filter(apiKey1 -> apiKey1.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", id));

        apiKey.setEnabled(false);
        apiKeyRepository.save(apiKey);
    }

    @Override
    @Transactional
    public ApiKeyCreateResponse rotateKey(UUID merchantId, UUID keyId) {
        //check if API Id for merchant is there or not
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(apiKey1 -> apiKey1.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("Api Key", keyId));

        if(! apiKey.getEnabled()) throw new RuntimeException("Can not revoke disabled API key");

        String newRawSecret = RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret);
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24));

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyID(),
                newRawSecret, apiKey.getEnvironment());
    }
}
