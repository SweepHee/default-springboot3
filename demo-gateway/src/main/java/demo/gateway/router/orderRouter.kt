package demo.gateway.router

import demo.common.annotation.Router
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean

@Router
class OrderRouter(
    @Value("\${route.order-api.v1.base-url}")
    val url: String,
) {

    val gatewayPath: String = "/providers/order-api/v1"

    @Bean
    fun locator(builder: RouteLocatorBuilder): RouteLocator = builder.routes()
        .route("order-api") {
            it.path("${gatewayPath}/**")
                .filters{ filter -> filter.rewritePath("$gatewayPath(?<servicePath>.*)", "/\${servicePath}") }
                .uri(url)
        }
        .build()


}