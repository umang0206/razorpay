package com.codingshuttle.razorpay.merchant.mapper;

import com.codingshuttle.razorpay.common.enums.Environment;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.codingshuttle.razorpay.merchant.dto.response.ApiKeyResponse;
import com.codingshuttle.razorpay.merchant.entity.ApiKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-09T12:04:04+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.1 (Oracle Corporation)"
)
@Component
public class ApiKeyMapperImpl implements ApiKeyMapper {

    @Override
    public ApiKeyCreateResponse toCreateResponse(ApiKey apiKey) {
        if ( apiKey == null ) {
            return null;
        }

        UUID id = null;
        Environment environment = null;

        id = apiKey.getId();
        environment = apiKey.getEnvironment();

        String keyId = null;
        String keySecret = null;

        ApiKeyCreateResponse apiKeyCreateResponse = new ApiKeyCreateResponse( id, keyId, keySecret, environment );

        return apiKeyCreateResponse;
    }

    @Override
    public List<ApiKeyResponse> toApiKeyResponseList(List<ApiKey> apiKeyList) {
        if ( apiKeyList == null ) {
            return null;
        }

        List<ApiKeyResponse> list = new ArrayList<ApiKeyResponse>( apiKeyList.size() );
        for ( ApiKey apiKey : apiKeyList ) {
            list.add( apiKeyToApiKeyResponse( apiKey ) );
        }

        return list;
    }

    protected ApiKeyResponse apiKeyToApiKeyResponse(ApiKey apiKey) {
        if ( apiKey == null ) {
            return null;
        }

        UUID id = null;
        Environment environment = null;
        boolean enabled = false;
        LocalDateTime lastUsedAt = null;

        id = apiKey.getId();
        environment = apiKey.getEnvironment();
        if ( apiKey.getEnabled() != null ) {
            enabled = apiKey.getEnabled();
        }
        lastUsedAt = apiKey.getLastUsedAt();

        String keyId = null;
        LocalDateTime createdAt = null;

        ApiKeyResponse apiKeyResponse = new ApiKeyResponse( id, keyId, environment, enabled, lastUsedAt, createdAt );

        return apiKeyResponse;
    }
}
