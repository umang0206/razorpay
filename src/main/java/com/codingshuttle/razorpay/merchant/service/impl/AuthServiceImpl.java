package com.codingshuttle.razorpay.merchant.service.impl;

import com.codingshuttle.razorpay.common.enums.MerchantStatus;
import com.codingshuttle.razorpay.common.enums.UserRole;
import com.codingshuttle.razorpay.common.execptions.DuplicateResourceException;
import com.codingshuttle.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.codingshuttle.razorpay.merchant.dto.response.MerchantResponse;
import com.codingshuttle.razorpay.merchant.entity.AppUser;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import com.codingshuttle.razorpay.merchant.mapper.MerchantMapper;
import com.codingshuttle.razorpay.merchant.repository.AppUserRepository;
import com.codingshuttle.razorpay.merchant.repository.MerchantRepository;
import com.codingshuttle.razorpay.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
//with the help of this we do not need to add dependency injection it will automatically create the injection
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    // since we are using @RequiredArgsConstructor we don't need to add dependency injection, this will automatically inject dependency.
    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;
    private final MerchantMapper merchantMapper;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {

        if(merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL", "Merchant with email Id already exists: " + request.email());
        }

        Merchant merchant = merchantMapper.toEntityFromSignUpRequest(request);
        merchant.setStatus(MerchantStatus.KYC_PENDING);
        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password()) //TODO: we have to encrypt the password using Bcrypt
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);

        return merchantMapper.toMerchantResponse(merchant);
    }
}
