package org.cerion.stocksapi.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass


class PriceKey : Serializable {
    private lateinit var symbol: String
    private lateinit var date: Date
}

@Entity
@IdClass(PriceKey::class)
data class Price(
        @JsonIgnore
        @Id val symbol: String,
        @Id val date: Date,
        @Column val open: Float,
        @Column val high: Float,
        @Column val low: Float,
        @Column val close: Float,
        @Column val volume: Float)


