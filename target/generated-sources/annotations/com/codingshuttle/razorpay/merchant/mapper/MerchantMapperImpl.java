package com.codingshuttle.razorpay.merchant.mapper;

import com.codingshuttle.razorpay.common.enums.BusinessType;
import com.codingshuttle.razorpay.common.enums.MerchantStatus;
import com.codingshuttle.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.codingshuttle.razorpay.merchant.dto.response.MerchantResponse;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T12:04:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class MerchantMapperImpl implements MerchantMapper {

    @Override
    public Merchant toEntityFromSignUpRequest(MerchantSignupRequest merchantSignupRequest) {
        if ( merchantSignupRequest == null ) {
            return null;
        }

        Merchant.MerchantBuilder merchant = Merchant.builder();

        merchant.name( merchantSignupRequest.name() );
        merchant.email( merchantSignupRequest.email() );
        merchant.businessName( merchantSignupRequest.businessName() );
        merchant.businessType( merchantSignupRequest.businessType() );

        return merchant.build();
    }

    @Override
    public MerchantResponse toMerchantResponse(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String email = null;
        String businessName = null;
        BusinessType businessType = null;

        id = merchant.getId();
        name = merchant.getName();
        email = merchant.getEmail();
        businessName = merchant.getBusinessName();
        businessType = merchant.getBusinessType();

        MerchantStatus merchantStatus = null;

        MerchantResponse merchantResponse = new MerchantResponse( id, name, email, businessName, businessType, merchantStatus );

        return merchantResponse;
    }
}
