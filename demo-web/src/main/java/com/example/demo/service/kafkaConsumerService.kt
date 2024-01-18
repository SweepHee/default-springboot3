package com.example.demo.service

import com.example.demo.model.Message
import com.example.demo.model.Topic
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {

    private val objectMapper = ObjectMapper()
    private val logger = KotlinLogging.logger{}


//    @KafkaListener(topics = [ Topic.TOPIC5 ])
//    fun listenMessage(jsonMessage: String) {
//        try {
//            val message = objectMapper.readValue(jsonMessage, Message::class.java)
//            logger.info { ">>> ${message.name}::${message.message}" }
//        } catch(e: Exception) {
//            logger.error { "[ ERROR: KAFKA_CONSUME_LISTEN_MESSAGE ]: ${e.message}" }
//        }
//    }

}