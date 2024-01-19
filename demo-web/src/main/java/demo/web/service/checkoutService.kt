package demo.web.service


import demo.web.entity.Checkout
import demo.web.repository.CheckoutRepository
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