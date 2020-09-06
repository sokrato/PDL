package com.xux.elib.jpa.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Filet(
        @Id @GeneratedValue
        var id: Int? = null,
        @Column(length = 255, nullable = false)
        var name: String = "",
        @Column(nullable = false)
        var size: Long = 0,
        @Column(length = 6)
        var format: String = "",
        @Column(length = 100)
        var storeKey: String = "",
        var createdAt: LocalDateTime = LocalDateTime.now()
)
