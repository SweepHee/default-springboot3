package demo.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("orders")
class OrderController {

    val logger = KotlinLogging.logger {}
    val objectMapper = ObjectMapper()

    @ModelAttribute
    fun modelAttribute(model: Model) {
        model.addAttribute("product", "beg")
        model.addAttribute("amount", 50000)
        model.addAttribute("count", 1)
    }

    @GetMapping
    fun index(model: Model): String {
        return objectMapper.writeValueAsString(model.asMap())
    }

    @PostMapping
    fun store(model: Model): String {
        val savedProduct = model.asMap()
        val request = mutableMapOf<Any, Any>(
            "result" to true,
            "data" to objectMapper.writeValueAsString(savedProduct),
            "error" to false,
        )
        return objectMapper.writeValueAsString(request)
    }

}