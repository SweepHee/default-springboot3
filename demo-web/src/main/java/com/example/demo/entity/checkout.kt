package com.example.demo.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "CHECKOUT_TABLE")
data class Checkout(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var checkoutId: Long? = null,
    var memberId: Long? = null,
    var productId: Long? = null,
    var amount: Long? = null,
    var shippingAddress: Long? = null,
    var createdAt: LocalDateTime = LocalDateTime.now()
)