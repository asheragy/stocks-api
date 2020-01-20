package org.cerion.stocksapi.repository

import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.entities.PriceKey
import org.cerion.stocksapi.entities.Symbol
import org.springframework.data.jpa.repository.JpaRepository

interface SymbolRepository : JpaRepository<Symbol, String>

interface PriceRepository : JpaRepository<Price, PriceKey>