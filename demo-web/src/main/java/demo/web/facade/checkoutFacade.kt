package demo.web.facade

import demo.web.service.CheckoutService
import demo.web.service.KafkaProducerService
import com.fasterxml.jackson.databind.ObjectMapper
import demo.common.annotation.Facade
import demo.common.model.KafkaTopic
import demo.data.model.Checkout as CheckoutModel
import demo.data.entity.Checkout
import jakarta.annotation.Resource
import mu.KotlinLogging
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Facade
class CheckoutFacade(
    private val checkoutService: CheckoutService,
    private val kafkaProducerService: KafkaProducerService
) {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Resource(name = "modelMapper")
    lateinit var modelMapper: ModelMapper

    val logger = KotlinLogging.logger {}


    // NOTE : 디비 저장 후 카프카 발행
    fun saveAndProduce(model: CheckoutModel): Long? {
        try {
            val toEntity = modelMapper.map(model, Checkout::class.java)
            val entity = checkoutService.save(toEntity)
            val toModel = modelMapper.map(entity, CheckoutModel::class.java)
            val toJson = objectMapper.writeValueAsString(toModel)
            kafkaProducerService.send(KafkaTopic.Topic.CHECKOUT_COMPLETE_TOPIC, toJson)
            return toModel.checkoutId
        } catch (e: Exception) {
            logger.error { "[ ERROR: SAVE_AND_PRODUCE ] ${e.message}" }
            throw e
        }
    }

}