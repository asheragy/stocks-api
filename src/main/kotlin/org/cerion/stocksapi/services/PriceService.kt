package org.cerion.stocksapi.services

import org.cerion.stocks.core.model.Interval
import org.cerion.stocks.core.web.clients.YahooFinance
import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.repository.PriceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PriceService @Autowired constructor(val repo: PriceRepository) {

    val yahoo = YahooFinance.instance

    fun get(symbol: String, interval: Interval): List<Price> {
        val repoValues = repo.findAll().filter { it.symbol == symbol } // TODO should not query all records
        if (repoValues.isNotEmpty())
            return repoValues

        val result = yahoo.getPrices(symbol, interval, 10)
        val prices = result.map {
            Price(symbol, it.date, it.open, it.high, it.low, it.close, it.volume)
        }

        repo.saveAll(prices)
        repo.flush()

        return prices
    }
}