package org.cerion.stocksapi.controllers

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.cerion.stocksapi.entities.Price
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class PriceControllerIntegrationTest {
    private val mapper = ObjectMapper()

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `initial data retrieved from web`() {
        val response = restTemplate.getForEntity("http://localhost:$port/prices/SPY/weekly", List::class.java)
        val prices: List<Price> = mapper.convertValue(response.body!!, object : TypeReference<List<Price>>() {})

        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(prices.isNotEmpty())
        assertTrue(prices[0].date!!.time > 0)
        assertTrue(prices[0].close!! > 0)
    }
}