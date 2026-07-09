package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import com.codingshuttle.razorpay.common.enums.Environment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "api_key",
        indexes = {
            @Index(name = "idx_api_key_merchant", columnList = "merchant_id, environment, enabled")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiKey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false, length = 50, unique = true)
    private String keyID;

    @Column(nullable = false, length = 200)
    private String keySecretHash;

    @Column(length = 200)
    private String previousKeySecretHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Environment environment;

    // This field indicates whether the API key is currently active and can be used for authentication. If set to false, the key is considered inactive and should not be used for any API requests.
    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    private LocalDateTime lastUsedAt;
    private LocalDateTime rotatedAt;
    private LocalDateTime gracePeriodExpiresAt;
}
