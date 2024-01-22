package demo.kafka.streams.listener

import demo.common.model.KafkaTopic
import demo.common.util.jsonValue
import demo.kafka.streams.model.Stream
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.TimeWindows
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class StreamListener {

    val INPUT_TOPIC = KafkaTopic.Topic.CHECKOUT_COMPLETE_TOPIC.topic
    val OUTPIC_TOPIC = KafkaTopic.Topic.CHECKOUT_OUTPUT_TOPIC.topic

    @Bean
    fun kafkaStream(builder: StreamsBuilder): KStream<String, String> {
        return builder.stream<String, String>(INPUT_TOPIC)
            .apply {
                 map { _, value -> KeyValue(value.jsonValue("productId").toLong(), value.jsonValue("amount").toLong()) }
                .groupByKey( Grouped.with(Serdes.Long(), Serdes.Long()) )
                .windowedBy (TimeWindows.ofSizeAndGrace(Duration.ofMinutes(1), Duration.ZERO))
                .reduce(Long::plus)
                .toStream{ key, _ -> key.key() }
                .mapValues(Stream::sendingJson)
                .selectKey{ _, _ -> null }
                .to(OUTPIC_TOPIC, Produced.with(null, Serdes.String()))
            }
        }


}

