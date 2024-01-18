package com.example.demo.facade

import com.example.demo.model.Checkout
import com.example.demo.model.TopicName
import com.example.demo.service.CheckoutService
import com.example.demo.service.KafkaProducerService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.Resource
import mu.KotlinLogging
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
class CheckoutFacade(
    private val checkoutService: CheckoutService,
    private val kafkaProducerService: KafkaProducerService
) {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Resource(name = "modelMapper")
    lateinit var modelMapper: ModelMapper

    val logger = KotlinLogging.logger {}

    fun saveAndProduce(model: Checkout): Long? {
        try {
            val toEntity = modelMapper.map(model, com.example.demo.entity.Checkout::class.java)
            val entity = checkoutService.save(toEntity)
            val toModel = modelMapper.map(entity, Checkout::class.java)
            val toJson = objectMapper.writeValueAsString(toModel)
            kafkaProducerService.send(TopicName.CHECKOUT_COMPLETE_TOPIC, toJson)
            return toModel.checkoutId
        } catch (e: Exception) {
            logger.error { "[ ERROR: SAVE_AND_PRODUCE ] ${e.message}" }
            throw e
        }
    }

}


