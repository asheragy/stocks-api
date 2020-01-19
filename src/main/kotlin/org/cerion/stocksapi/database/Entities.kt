package org.cerion.stocksapi.database

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Symbol(
        @Id
        val id: String,
        @Column
        val name: String,
        @Column
        val exchange: String)