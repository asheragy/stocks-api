package org.cerion.stocksapi.controllers

import org.cerion.stocksapi.entities.Symbol
import org.cerion.stocksapi.services.SymbolService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SymbolController @Autowired constructor(val service: SymbolService) {

    @GetMapping("/symbols")
    fun getAll(): List<Symbol> {
        return service.getAll()
    }

    @GetMapping("/symbols/{id}")
    fun get(@PathVariable("id") id: String): ResponseEntity<Symbol> {
        try {
            return ResponseEntity.ok(service.get(id)!!)
        }
        catch(e: Exception) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/symbols/{id}")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Unit> {
        if (service.delete(id))
            return ResponseEntity.noContent().build()
        else
            return ResponseEntity.notFound().build()
    }
}