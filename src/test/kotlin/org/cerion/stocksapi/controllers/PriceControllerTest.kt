package org.cerion.stocksapi.controllers

import com.nhaarman.mockitokotlin2.*
import org.cerion.stocks.core.model.Interval
import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.services.PriceService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.io.FileNotFoundException
import java.util.*

@WebMvcTest(PriceController::class)
internal class PriceControllerTest {

    @Autowired private lateinit var mockMvc: MockMvc
    @MockBean private lateinit var service: PriceService

    @BeforeEach
    fun init() {
        given(service.get(eq("DNE"), any())).willAnswer { throw FileNotFoundException() }
        whenever(service.get(eq("TEST"), eq(Interval.DAILY))).thenReturn(listOf(Price("TEST", Interval.DAILY, Date(), 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)))
    }

    @Test
    fun `happy path`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices/TEST/daily"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[{\"date\":\"2020-01-22\",\"open\":1.0,\"high\":1.0,\"low\":1.0,\"close\":1.0,\"volume\":1.0}]"))

        Mockito.verify(service, Mockito.times(1)).get(any(), any())
    }

    @Test
    fun `400 when invalid interval`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices/TEST/biweekly"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.content().string("invalid interval 'biweekly'")) // TODO pattern match since exact string is not important

        Mockito.verify(service, Mockito.times(0)).get(any(), any())
    }

    @Test
    fun `404 when symbol does not exist`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/prices/DNE/daily"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `500 when service lookup failed`() {
        given(service.get(any(), any())).willAnswer { throw Exception() }
        mockMvc.perform(MockMvcRequestBuilders.get("/prices/something/daily"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError)
    }
}