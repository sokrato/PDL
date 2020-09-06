package com.xux.elib.jpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Publisher(
    @Id @GeneratedValue
    var id: Int? = null,
    @Column(length = 20, nullable = false)
    var shortName: String = "",
    @Column(length = 120, nullable = false)
    val fullName: String = "",
    @Column(length = 2, nullable = false)
    var country: String = "us"
) {
    companion object {
        val UnNamed = Publisher(1)
    }
}