package org.cerion.stocksapi.services

import org.cerion.stocks.core.model.Interval
import org.cerion.stocks.core.web.clients.YahooFinance
import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.repository.PriceRepository
import org.hibernate.criterion.Example
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class PriceService @Autowired constructor(
        private val repo: PriceRepository,
        @Qualifier("yahooService") private val yahoo: YahooFinance) {

    fun get(symbol: String, interval: Interval): List<Price> {
        val repoValues = repo.findAll(symbol, interval.ordinal)
        if (repoValues.isNotEmpty())
            return repoValues

        val result = yahoo.getPrices(symbol, interval, 10)
        val prices = result.map {
            Price(symbol, interval, it.date, it.open, it.high, it.low, it.close, it.volume)
        }

        repo.saveAll(prices)
        repo.flush()

        return prices
    }
}