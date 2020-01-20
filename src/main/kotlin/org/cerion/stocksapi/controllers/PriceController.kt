package org.cerion.stocksapi.controllers

import org.cerion.stocks.core.model.Interval
import org.cerion.stocksapi.entities.Price
import org.cerion.stocksapi.services.PriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class PriceController @Autowired constructor(val service: PriceService) {

    @GetMapping("/prices/{symbol}/{interval}")
    fun get(@PathVariable("symbol") symbol: String, @PathVariable("interval") intervalParam: String): ResponseEntity<List<Price>> {

        val interval: Interval = when(intervalParam) {
            "daily" -> Interval.DAILY
            "weekly" -> Interval.WEEKLY
            "monthly" -> Interval.MONTHLY
            else -> null
        } ?: return ResponseEntity.notFound().build()

        try {
            return ResponseEntity.ok(service.get(symbol, interval)!!)
        }
        catch(e: Exception) {
            return ResponseEntity.notFound().build()
        }
    }
}