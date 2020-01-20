package org.cerion.stocksapi.services


import org.cerion.stocks.core.web.clients.Tiingo
import org.cerion.stocksapi.StocksApiApplication
import org.cerion.stocksapi.database.Symbol
import org.cerion.stocksapi.database.SymbolRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SymbolService @Autowired constructor(val repo: SymbolRepository) {
    private val tiingo = Tiingo(StocksApiApplication.getTiingoApiKey())
    private val logger: Logger = LoggerFactory.getLogger(SymbolService::class.java)

    fun getAll(): List<Symbol> {
        return repo.findAll()
    }

    fun get(id: String): Symbol? {
        val result = repo.findById(id).orElseGet {
            logger.info("Looking up $id")
            val lookup = tiingo.getSymbol(id)
            if (lookup != null)
                repo.saveAndFlush(lookup.convert())
            else
                null
        }

        return result
    }

    fun delete(id: String): Boolean {
        if(!repo.existsById(id))
            return false

        repo.deleteById(id)
        return true
    }

}

//fun org.cerion.stocksapi.database.Symbol.convert(): Symbol = Symbol(id, name, exchange)
fun org.cerion.stocks.core.model.Symbol.convert(): Symbol = Symbol(symbol, name?: "", exchange?: "")