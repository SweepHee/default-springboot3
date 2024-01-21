package demo.web

import demo.common.annotation.EnableKafkaProducerConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableKafkaProducerConfig
@ComponentScan("demo")
class WebApplication {
	companion object {
		const val appName = "demo-web"
	}
}

fun main(args: Array<String>) {
	runApplication<WebApplication>(*args)
}