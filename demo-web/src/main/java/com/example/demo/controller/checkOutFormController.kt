package com.example.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CheckOutFormController {

    val logger = KotlinLogging.logger {}
    val objectMapper = ObjectMapper()

    @ModelAttribute
    fun global(model: Model) {
        model.addAttribute("hello", "world")
        model.addAttribute("test", "testWorld!")
    }

    @GetMapping("checkOutForm")
    fun index(model: Model): String {
        logger.info("checkOutForm.....")
        return "checkout/form"
    }

    @GetMapping("hello")
    @ResponseBody
    fun test(model: Model): String {
        val json = objectMapper.writeValueAsString(model.asMap())
        println(json)
        return "hello world"
    }

}