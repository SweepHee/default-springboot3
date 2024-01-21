package demo.consumer

import demo.common.annotation.EnableKafkaConsumerConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@EnableKafkaConsumerConfig
@SpringBootApplication
@ComponentScan("demo")
class ConsumeApplication {
	companion object {
		const val appName = "demo-consumer"
	}
}

fun main(args: Array<String>) {
	runApplication<ConsumeApplication>(*args)
}