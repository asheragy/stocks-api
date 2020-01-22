package org.cerion.stocksapi.controllers

import org.cerion.stocks.core.model.Interval
import org.cerion.stocksapi.services.PriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.io.FileNotFoundException


@RestController
class PriceController @Autowired constructor(val service: PriceService) {

    @GetMapping("/prices/{symbol}/{interval}")
    fun get(@PathVariable("symbol") symbol: String, @PathVariable("interval") intervalParam: String): ResponseEntity<Any> {

        return try {
            val interval: Interval = when(intervalParam) {
                "daily" -> Interval.DAILY
                "weekly" -> Interval.WEEKLY
                "monthly" -> Interval.MONTHLY
                else -> throw IllegalArgumentException()
            }

            ResponseEntity.ok(service.get(symbol, interval)!!)
        }
        catch(e: Exception) {
            when(e) {
                is IllegalArgumentException -> ResponseEntity("invalid interval '$intervalParam'", HttpStatus.BAD_REQUEST)
                // TODO api needs to have exceptions for various error types, this one is only used for unit test currently
                is FileNotFoundException -> ResponseEntity.notFound().build()
                else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }
    }
}