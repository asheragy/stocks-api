package org.cerion.stocksapi.repository

import org.cerion.stocks.core.model.Interval
import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.entities.PriceKey
import org.cerion.stocksapi.entities.Symbol
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SymbolRepository : JpaRepository<Symbol, String>

interface PriceRepository : JpaRepository<Price, PriceKey> {

    @Query(value = "SELECT * FROM price p WHERE p.symbol = :symbol AND p.price_interval = :interval", nativeQuery = true)
    fun findAll(@Param("symbol") symbol: String, @Param("interval") interval: Int): List<Price>
}