package org.cerion.stocksapi.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.coxautodev.graphql.tools.GraphQLResolver
import org.cerion.stocks.core.model.Interval
import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.entities.Symbol
import org.cerion.stocksapi.services.PriceService
import org.cerion.stocksapi.services.SymbolService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Query @Autowired constructor(private val service: SymbolService) : GraphQLQueryResolver {

    fun getSymbol(id: String): Symbol {
        return service.get(id)!!
    }
}

@Component
class SymbolResolver @Autowired constructor(private val service: PriceService) : GraphQLResolver<Symbol> {

    fun getDaily(symbol: Symbol): List<Price> = service.get(symbol.id, Interval.DAILY)
    fun getWeekly(symbol: Symbol): List<Price> = service.get(symbol.id, Interval.WEEKLY)
    fun getMonthly(symbol: Symbol): List<Price> = service.get(symbol.id, Interval.MONTHLY)
}