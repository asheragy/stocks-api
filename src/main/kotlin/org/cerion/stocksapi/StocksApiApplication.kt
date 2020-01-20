package org.cerion.stocksapi

import org.cerion.stocksapi.entities.Symbol
import org.cerion.stocksapi.repository.SymbolRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.util.*


@SpringBootApplication
class StocksApiApplication {

	companion object {
		// TODO move to gradle so its a build dependency
		fun getTiingoApiKey(): String {
			val propFile = "apikey.properties"
			val apikeyProperties = Properties()
			apikeyProperties.load(FileInputStream(propFile))

			return apikeyProperties["tiingo"] as String
		}
	}
}

fun main(args: Array<String>) {
	runApplication<StocksApiApplication>(*args)
}

@Component
class DataLoader @Autowired constructor(private val repo: SymbolRepository) : ApplicationRunner {
	override fun run(args: ApplicationArguments) {
		repo.save(Symbol("SPY", "S&P 500", "Index"))
	}
}


