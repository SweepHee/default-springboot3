package demo.web.repository

import demo.web.entity.Checkout
import org.springframework.data.jpa.repository.JpaRepository

interface CheckoutRepository: JpaRepository<Checkout, Long> {

}