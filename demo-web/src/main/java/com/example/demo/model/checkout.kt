package com.example.demo.model

import java.util.Date

data class Checkout(
    var checkoutId: Long? = null,
    var memberId: Long? = null,
    var productId: Long? = null,
    var amount: Long? = null,
    var shippingAddress: String? = null,
    var createdAt: Date? = null
)