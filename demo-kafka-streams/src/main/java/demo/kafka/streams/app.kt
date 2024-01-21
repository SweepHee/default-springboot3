package demo.kafka.streams

import demo.common.annotation.EnableKafkaStreamConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableKafkaStreamConfig
@ComponentScan("demo")
class KafkaStreamsApplication {
	companion object {
		const val appName = "demo-kafka-streams"
	}
}

fun main(args: Array<String>) {
	runApplication<KafkaStreamsApplication>(*args)
}