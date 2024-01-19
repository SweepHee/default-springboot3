package demo.consumer.service

import demo.data.entity.Checkout
import demo.data.repository.CheckoutRepository
import org.springframework.stereotype.Service

@Service
class CheckoutService(
    val checkoutRepository: CheckoutRepository
) {
    fun save(checkout: Checkout): Checkout {
        return checkoutRepository.save(checkout)
    }
}