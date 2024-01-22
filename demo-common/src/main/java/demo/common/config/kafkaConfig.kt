package demo.common.config

import demo.common.annotation.EnableKafkaConsumerConfig
import demo.common.annotation.EnableKafkaProducerConfig
import demo.common.annotation.EnableKafkaStreamConfig
import demo.common.model.Message
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.streams.StreamsConfig
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaStreamsConfiguration
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

data class KafkaConstant(
    val BOOTSTRAP_SERVER: String = "localhost:29092",
    val GROUP_ID: String = "group3",
)

@Configuration
@ConditionalOnBean(annotation = [EnableKafkaProducerConfig::class])
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

@Configuration
@EnableKafka
@ConditionalOnBean(annotation = [EnableKafkaConsumerConfig::class])
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


@Configuration
@EnableKafka
@EnableKafkaStreams
@ConditionalOnBean(annotation = [EnableKafkaStreamConfig::class])
class KafkaStreamConfiguration {

    @Bean(name = [ KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME ])
    fun kafkaStreamConfiguration() = KafkaStreamsConfiguration(hashMapOf<String, Any>(
        StreamsConfig.APPLICATION_ID_CONFIG to "kafka-streams",
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to KafkaConstant().BOOTSTRAP_SERVER,
        StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to Serdes.String().javaClass,
        StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to Serdes.String().javaClass,
        StreamsConfig.COMMIT_INTERVAL_MS_CONFIG to 1000
    ))

}