package org.cerion.stocksapi.services

import com.nhaarman.mockitokotlin2.*
import org.cerion.stocks.core.PriceList
import org.cerion.stocks.core.PriceRow
import org.cerion.stocks.core.model.Interval
import org.cerion.stocks.core.web.clients.YahooFinance
import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.repository.PriceRepository
import org.junit.jupiter.api.Test
import java.util.*

internal class PriceServiceTest {

    private val repo: PriceRepository = mock()
    private val yahoo: YahooFinance = mock()
    private val service = PriceService(repo, yahoo)

    @Test
    fun `use repository data if available`() {
        val row = PriceRow(Date(), 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)
        val pricelist = PriceList("TEST", listOf(row))

        whenever(repo.findAll(any(), any())).thenReturn(emptyList())
        whenever(repo.findAll(any(), eq(Interval.WEEKLY.ordinal))).thenReturn(listOf(Price("A", Interval.WEEKLY, Date(), 1.0f, 1.0f, 1.0f, 1.0f, 1.0f)))
        whenever(yahoo.getPrices(any(), any(), any<Int>())).thenReturn(pricelist)

        service.get("TEST", Interval.DAILY)
        service.get("TEST", Interval.WEEKLY)
        service.get("TEST", Interval.MONTHLY)

        // Weekly exists local so web should be called 1 less time than repository
        verify(repo, times(3)).findAll(any(), any())
        verify(yahoo, times(2)).getPrices(any(), any(), any<Int>())
    }
}