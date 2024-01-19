package demo.web.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class KafkaTest(
    @Autowired
    var mockMvc: MockMvc
) {





    @Test
    fun `프로듀서 발행 테스트` () {
//        val producer1 = Producer1()
//        producer1.produce()
//        producer1.consume()
    }

    @Test
    fun `testPostSubmitCheckout` () {
        mockMvc.perform(post("/submitCheckOut")
            .param("memberId", "10001")
            .param("productId", "200001")
            .param("amount", "33000")
            .param("shippingAddress", "546"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }



}