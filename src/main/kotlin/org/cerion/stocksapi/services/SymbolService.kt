package org.cerion.stocksapi.services


import org.cerion.stocks.core.web.clients.Tiingo
import org.cerion.stocksapi.StocksApiApplication
import org.cerion.stocksapi.entities.Symbol
import org.cerion.stocksapi.repository.SymbolRepository
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
        return repo.findById(id).orElseGet {
            logger.info("Looking up $id")
            val lookup = tiingo.getSymbol(id)
            if (lookup != null)
                repo.saveAndFlush(lookup.convert())
            else
                null
        }
    }

    fun delete(id: String): Boolean {
        if(!repo.existsById(id))
            return false

        repo.deleteById(id)
        return true
    }

}

fun org.cerion.stocks.core.model.Symbol.convert(): Symbol = Symbol(symbol, name?: "", exchange?: "")