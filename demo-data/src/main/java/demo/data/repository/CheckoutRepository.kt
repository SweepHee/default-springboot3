package demo.data.repository

import demo.data.entity.Checkout
import org.springframework.data.jpa.repository.JpaRepository

interface CheckoutRepository: JpaRepository<Checkout, Long> {

}