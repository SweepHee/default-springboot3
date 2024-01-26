package demo.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("demo")
class GatewayApplication {
    companion object {
        const val appName = "demo-gateway"
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}