package demo.gateway

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import demo.common.util.jsonValue
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class GatewayTest(
    @Autowired
    var webClient: WebTestClient
) {



    @Test
    fun `test` () {

    }

    @Test
    fun `ORDER GATEWAY TEST` () {
        // given - 헤더 설정이 필요한 경우
        stubFor(get(urlEqualTo("/providers/order-api/v1/orders"))
            .willReturn(aResponse()
                .withBody("{\"headers\":{\"Role\":\"hello-api\"}}")
                .withHeader("Content-Type", "application/json")
            )
        )

        // when
        val response: String? = webClient
            .get().uri("/providers/order-api/v1/orders")
            .exchange()
            .expectStatus().isOk
            .returnResult(String::class.java).responseBody.blockFirst()

        // then
        assertNotNull(response)

        val product = response?.jsonValue("product")
        val amount = response?.jsonValue("amount")
        val count = response?.jsonValue("count")

        println("product: ${product}, amount: ${amount}, count: $count")

        assertEquals(product, "beg")
        assertEquals(amount, "50000")
        assertEquals(count, "1")

    }

    @Test
    fun `ORDER 프로덕트 저장 성공 테스트` () {
        // given - 헤더 설정이 필요한 경우
        stubFor(get(urlEqualTo("/providers/order-api/v1/orders"))
            .willReturn(aResponse()
                .withBody("{\"headers\":{\"Role\":\"hello-api\"}}")
                .withHeader("Content-Type", "application/json")
            )
        )

        // when
        val response: String? = webClient
            .post().uri("/providers/order-api/v1/orders")
            .exchange()
            .expectStatus().isOk
            .returnResult(String::class.java).responseBody.blockFirst()

        // then
        println(response)

        assertNotNull(response)

        val result = response?.jsonValue("result")
        val data = response?.jsonValue("data")
        val error = response?.jsonValue("error")

        assertTrue { result.toString() == "true" }
        assertFalse { error.toString() == "true" }

        val product = data?.jsonValue("product")
        val amount = data?.jsonValue("amount")
        val count = data?.jsonValue("count")

        println("product: ${product}, amount: ${amount}, count: $count")

        assertEquals(product, "beg")
        assertEquals(amount, "50000")
        assertEquals(count, "1")

    }

}