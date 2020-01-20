package org.cerion.stocksapi.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class SymbolControllerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `list returns default items`() {
        val response = restTemplate.getForEntity("http://localhost:$port/symbols", List::class.java)
        val symbol = response.body!![0] as LinkedHashMap<*, *>

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("SPY", symbol["id"])
    }
}