package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "merchant_webhook_config", indexes = {
        @Index(name = "idx_merchant_webhook_config_merchant_id", columnList = "merchant_id, enabled")
})
public class MerchantWebhookConfig extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(name = "target_url", nullable = false, length = 500)
    private String targetUrl;

    @Column(length = 255)
    private String webhookSecretHash;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(length = 255)
    private String eventTypes;
}
