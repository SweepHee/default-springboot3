package demo.web.service


import demo.data.entity.Checkout
import demo.data.repository.CheckoutRepository
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