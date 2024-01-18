package com.example.demo.controller

import com.example.demo.model.Message
import com.example.demo.model.Topic
import com.example.demo.model.TopicName
import com.example.demo.service.KafkaProducerService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(
    val kafkaProducerService: KafkaProducerService
) {

    @RequestMapping("/publish")
    fun publish(message: String): String {
        kafkaProducerService.send(TopicName.TOPIC5, message)
        return "published a message : $message"
    }

    @RequestMapping("/publish2")
    fun publishWithCallback(message: String): String {
        kafkaProducerService.sendWithCallback(TopicName.TOPIC5, message)
        return "published a message with callback: $message"
    }

    @RequestMapping("/publish3")
    fun publishWithCallback(message: Message): String {
        kafkaProducerService.sendJson(message)
        return "published a Json message: ${message.name}, ${message.message}"
    }

}