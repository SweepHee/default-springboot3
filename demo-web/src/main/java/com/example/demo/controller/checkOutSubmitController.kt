package com.example.demo.controller

import com.example.demo.facade.CheckoutFacade
import com.example.demo.model.Checkout
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CheckOutSubmitController(
    private val checkoutFacade: CheckoutFacade,
) {

    val logger = KotlinLogging.logger {}

    @PostMapping("submitCheckOut")
    fun index(checkout: Checkout,model: Model): String {
        logger.info { "hello world" }
        val checkoutId = checkoutFacade.saveAndProduce(checkout)
        model.addAttribute("checkoutId", checkoutId)
        return "checkout/form"
    }

}