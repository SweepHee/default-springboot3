package demo.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import demo.data.model.Checkout
import demo.web.facade.CheckoutFacade
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CheckOutFormController(
    private val checkoutFacade: CheckoutFacade,
) {

    val logger = KotlinLogging.logger {}
    val objectMapper = ObjectMapper()

    @ModelAttribute
    fun global(model: Model) {
        model.addAttribute("hello", "world")
        model.addAttribute("test", "testWorld!")
    }

    @GetMapping("checkOutForm")
    fun index(model: Model): String = "checkout/form"


    @PostMapping("submitCheckOut")
    fun index(checkout: Checkout, model: Model): String {
        checkoutFacade.saveAndProduce(checkout)
        return "redirect:/checkOutForm"
    }

}