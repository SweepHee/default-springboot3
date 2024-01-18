package com.example.demo.service


import com.example.demo.entity.Checkout
import com.example.demo.repository.CheckoutRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CheckoutService(
    private val checkoutRepository: CheckoutRepository
) {

    @Transactional
    fun save(checkout: Checkout): Checkout {
        return checkoutRepository.save(checkout)
    }



}