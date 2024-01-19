package demo.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan("demo")
class WebApplication {
	companion object {
		const val appName = "demo-web"
	}
}

fun main(args: Array<String>) {
	runApplication<WebApplication>(*args)
}