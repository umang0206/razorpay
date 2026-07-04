package com.codingshuttle.razorpay.merchant.entity;

import com.codingshuttle.razorpay.common.enums.UserRole;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    /* Many app users can belong to one merchant example if a merchant has multiple employees, they can all be associated with the same merchant.
       The fetch type is set to LAZY to avoid loading the merchant data unless it's explicitly accessed, which can improve performance.
       JoinColumn specifies the foreign key column in the app_user table that references the merchant table. The nullable = false constraint ensures that every app user must be associated with a merchant.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

}
