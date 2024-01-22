package demo.web.service


import demo.common.annotation.EnableKafkaProducerConfig
import demo.common.model.KafkaTopic
import demo.common.model.Message
import jakarta.annotation.Resource
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture


@Service
class KafkaProducerService{

    val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Resource(name = "kafkaJsonTemplate")
    lateinit var kafkaJsonTemplate: KafkaTemplate<String, Message>

    fun send(topic: KafkaTopic.Topic, message: String) {
        kafkaTemplate.send(topic.topic, message)
    }

    fun sendWithCallback(topic: KafkaTopic.Topic, message: String) {
        val future: CompletableFuture<SendResult<String, String>> = kafkaTemplate.send(topic.topic, message)

        future.whenComplete{ result, e ->
            if (e != null) {
                logger.error { "[ ERROR:KAFKA_SEND_WITH_CALLBACK ] message: ${e.message}" }
                logger.error { "[ ERROR:KAFKA_SEND_WITH_CALLBACK ] datetime: ${LocalDateTime.now()}" }
                throw IllegalArgumentException("kafka send with callback error")
            }
            println("Sent $message offset: ${result.recordMetadata.offset()}")
        }
    }

    fun sendJson(message: Message) = kafkaJsonTemplate.send(KafkaTopic.Topic.TOPIC5.topic, message)


}


