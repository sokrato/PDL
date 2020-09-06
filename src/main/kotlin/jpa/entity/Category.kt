package com.xux.elib.jpa.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Category(
        @Id @GeneratedValue
        var id: Int? = null,
        @Column(length = 30, nullable = false)
        var name: String = "",
        @Column(nullable = false)
        var rank: Int = 0,
        @Column(nullable = false)
        var parentId: Int = RootId
) {
    companion object {
        val RootId = 0
        val UnCategorized = Category(1, "UnCategorized", 9999)
    }
}