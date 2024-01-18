package com.example.demo.repository

import com.example.demo.entity.Checkout
import org.springframework.data.jpa.repository.JpaRepository

interface CheckoutRepository: JpaRepository<Checkout, Long> {

}