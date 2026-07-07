package com.codingshuttle.razorpay.merchant.service.impl;

import com.codingshuttle.razorpay.common.enums.MerchantStatus;
import com.codingshuttle.razorpay.common.enums.UserRole;
import com.codingshuttle.razorpay.common.execptions.DuplicateResourceException;
import com.codingshuttle.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.codingshuttle.razorpay.merchant.dto.response.MerchantResponse;
import com.codingshuttle.razorpay.merchant.entity.AppUser;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
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

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {

        if(merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL", "Merchant with email Id already exists: " + request.email());
        }

        Merchant merchant = Merchant.builder()
                .name(request.name())
                .businessName(request.businessName())
                .businessType(request.businessType())
                .email(request.email())
                .status(MerchantStatus.KYC_PENDING)
                .build();
        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password()) //TODO: we have to encrypt the password using Bcrypt
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);

        return new MerchantResponse(merchant.getId(), merchant.getName(),
                merchant.getEmail(), merchant.getBusinessName(),
                merchant.getBusinessType(),merchant.getStatus());
    }
}
