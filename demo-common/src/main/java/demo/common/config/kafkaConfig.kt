package demo.common.config

import demo.common.model.Message
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

data class KafkaConstant(
    val BOOTSTRAP_SERVER: String = "localhost:9092",
    val GROUP_ID: String = "group3",
)

@Configuration
class KafkaProducerConfig {

    private val BOOTSTRAP_SERVER = KafkaConstant().BOOTSTRAP_SERVER

    @Bean
    fun producerFactory(): ProducerFactory<String, String> = DefaultKafkaProducerFactory(HashMap<String, Any>()
        .apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        })

    @Bean
    fun producerFactoryForJson(): ProducerFactory<String, Message> = DefaultKafkaProducerFactory(HashMap<String, Any>()
        .apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer::class.java)
        })

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> = KafkaTemplate(producerFactory())

    @Bean
    fun kafkaJsonTemplate(): KafkaTemplate<String, Message> = KafkaTemplate(producerFactoryForJson())

}

@EnableKafka
@Configuration
class KafkaConsumerConfig {

    private val BOOTSTRAP_SERVER = KafkaConstant().BOOTSTRAP_SERVER
    private val GROUP_ID = KafkaConstant().GROUP_ID

    @Bean
    fun consumerFactory() = DefaultKafkaConsumerFactory<String, Any>(
        hashMapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to  BOOTSTRAP_SERVER,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        )
    )

    @Bean
    fun kafkaListenerContainerFactory() = ConcurrentKafkaListenerContainerFactory<String, String>()
        .apply {
            consumerFactory = consumerFactory()
        }

}