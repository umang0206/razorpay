package com.codingshuttle.razorpay.vault.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vault_card")
public class VaultCard {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @Column(length = 4, nullable = false)
    private String last_four;

    //bin means bank identification number, which is the first 6 digits of the card number
    @Column(length = 6, nullable = false)
    private String bin;

    //encryptedPan means encrypted primary account number, which is the full card number encrypted
    @Column(nullable = false)
    private byte[] encryptedPan;

    //encryptedDek means encrypted data encryption key, which is the key used to encrypt the card number
    @Column(nullable = false)
    private byte[] encryptedDek;

    //brand means the card brand, such as Visa, Mastercard, etc.
    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String expiryMonth;

    @Column(nullable = false)
    private String expiryYear;

    @Column(nullable = false)
    private String cardHolderName;

    private LocalDateTime deletedAt;
}
