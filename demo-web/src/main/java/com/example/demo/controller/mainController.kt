package com.example.demo.controller


//import example.demo.jooq.Tables.STAR
//import demo.web.jooq.Tables.STAR
import com.example.demo.repository.MemberRepository
import demo.web.jooq.Tables.STAR
import org.jooq.DSLContext
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController(
    private val memberRepository: MemberRepository,
    private val dsl: DSLContext,
) {

    @ModelAttribute
    fun models(model: Model) {
        model.addAttribute("hello", "world")
    }

    @GetMapping
    fun index(model: Model): String {
//        memberRepository.deleteById(1)
        model.addAttribute("test", "test!!!")
//        model.addAttribute("hello" ,"world...")
        return "index"
    }

    @GetMapping("/test")
    @ResponseBody
    fun getStar(): String {
        val test = dsl.select().from(STAR).fetch()
        println(test)
        return "hello world"
    }

}