package com.example.demo.controller


//import example.demo.jooq.Tables.STAR
//import demo.web.jooq.Tables.STAR
import demo.web.jooq.Tables.STAR
import org.jooq.DSLContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController(
    private val memberRepository: MemberRepository,
    private val dsl: DSLContext,
) {

    @GetMapping
    @ResponseBody
    fun index(): String {
//        memberRepository.deleteById(1)
        return "hello world!"
    }

    @GetMapping("/test")
    @ResponseBody
    fun getStar(): String {
        val test = dsl.select().from(STAR).fetch()
        println(test)
        return "hello world"
    }

}