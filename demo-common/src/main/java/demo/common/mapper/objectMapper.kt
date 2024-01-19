
package demo.common.mapper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper? {
        return ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

}


fun Any.test(): String {
    return "hello world"
}