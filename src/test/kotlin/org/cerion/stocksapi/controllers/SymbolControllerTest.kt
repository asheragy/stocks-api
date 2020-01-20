package org.cerion.stocksapi.controllers

import org.cerion.stocksapi.database.Symbol
import org.cerion.stocksapi.services.SymbolService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SymbolController::class)
internal class SymbolControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var controller: SymbolController

    @MockBean
    private lateinit var service: SymbolService

    @Test
    fun `default response on empty list`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/symbols"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"))

        verify(service, times(1)).getAll()
    }

    @Test
    fun `list returns all records`() {
        doReturn(listOf(Symbol("ID", "NAME", "EXCHANGE"), Symbol("A", "B", "C"), Symbol("X", "Y", "Z"))).`when`(service).getAll()

        val result = controller.getAll()
        assertEquals(3, result.size)
        assertEquals(result[2].exchange, "Z")
    }

}