package org.cerion.stocksapi.database

import org.springframework.data.jpa.repository.JpaRepository

interface SymbolRepository : JpaRepository<Symbol, String>