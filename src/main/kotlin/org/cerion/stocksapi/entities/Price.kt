package org.cerion.stocksapi.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.cerion.stocks.core.model.Interval
import java.io.Serializable
import java.util.*
import javax.persistence.*


class PriceKey : Serializable {
    private lateinit var symbol: String
    private lateinit var interval: Interval
    private lateinit var date: Date
}

@Entity
@IdClass(PriceKey::class)
data class Price(
        @JsonIgnore
        @Id
        val symbol: String,

        @JsonIgnore
        @Id
        @Column(name = "price_interval") // renamed since 'interval' is a reserved database word
        val interval: Interval,

        @Id
        val date: Date?,
        @Column val open: Float?,
        @Column val high: Float?,
        @Column val low: Float?,
        @Column val close: Float?,
        @Column val volume: Float?)


