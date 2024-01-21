package demo.consumer.service

import demo.common.model.KafkaGroup
import demo.consumer.facade.CheckoutFacade

import demo.common.model.KafkaTopic
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService(
    val checkoutFacade: CheckoutFacade,
){

    private val logger = KotlinLogging.logger{}

    @KafkaListener(topics = [ KafkaTopic.CHECKOUT_COMPLETE_TOPIC ], groupId = KafkaGroup.SHIPMENT_V1)
    fun recordListener(jsonMessage: String) {
        try {
            val checkout = checkoutFacade.saveConvertMessage(jsonMessage)
            logger.info { checkout }
        } catch(e: Exception) {
            logger.error { "[ ERROR: KAFKA_CONSUME_LISTEN_MESSAGE ]: ${e.message}" }
        }
    }

}