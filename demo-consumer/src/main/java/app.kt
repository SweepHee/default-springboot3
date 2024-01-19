package com.example.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

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