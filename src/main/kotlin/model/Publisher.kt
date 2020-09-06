package com.xux.elib.model

data class Publisher(
    var id: Int = 0,
    var shortName: String = "",
    var fullName: String = "",
    var country: String = "us"
) {
    companion object {
        val UnNamed = Publisher(1)
    }
}
