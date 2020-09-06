package com.xux.elib.model

data class Category(
    var id: Int = 0,
    var name: String = "",
    var rank: Int = 0,
    var parentId: Int = 0
) {
    companion object {
        val UnCategorized = Category(1, "UnCategorized", parentId = 1)
    }
}