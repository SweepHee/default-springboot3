package demo.consumer.facade

import demo.consumer.service.CheckoutService
import com.fasterxml.jackson.databind.ObjectMapper
import demo.data.model.Checkout
import jakarta.annotation.Resource
import org.modelmapper.ModelMapper
import demo.data.entity.Checkout as CheckoutEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CheckoutFacade(
    val checkoutService: CheckoutService,
) {

    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Resource
    lateinit var modelMapper: ModelMapper


    /**
    * 저장하고 model로 리턴
    * */
    fun saveConvertMessage(message: String): Checkout {

        try {
            val messageToEntity = objectMapper.readValue(message, CheckoutEntity::class.java)
            checkoutService.save(messageToEntity)
            return modelMapper.map(messageToEntity, Checkout::class.java)
        } catch(e: Exception) {
            throw e
        }

    }

}