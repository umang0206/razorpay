package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.enums.BusinessType;
import com.codingshuttle.razorpay.common.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "merchant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 20)
    private String contactNumber;

    @Column(length = 100)
    private String businessName;

    @Column(length = 50)
    @Enumerated(EnumType.STRING) // Store the enum as a string in the database not as an ordinal value like 0,1,2,3,4,5
    private BusinessType businessType;

    @Column(length = 200)
    private String websiteUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MerchantStatus status = MerchantStatus.KYC_PENDING;

    @Column(length = 20)
    private String gstID;

    @Column(length = 20)
    private String panNumber;

    @Column(length = 100)
    private String settlementBankAccount;

    @Column(length = 20)
    private String settlementBankIFSC;

    @Column(length = 100)
    private String settlementBankAccountHolderName;
}
