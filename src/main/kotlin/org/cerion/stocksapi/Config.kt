package org.cerion.stocksapi

import org.cerion.stocks.core.web.clients.YahooFinance
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration {

    @Bean
    fun yahooService(): YahooFinance = YahooFinance.instance

}