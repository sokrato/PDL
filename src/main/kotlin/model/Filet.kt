package com.xux.elib.model

import java.time.LocalDateTime

/**
 * a file
 */
data class Filet(
        var id: Int = 0,
        // APUE, 3rd
        var name: String = "",
        var size: Long = 0L,
        // zip, pdf, epub, jpg
        var format: String = "",
        var storeKey: String = "",
        var createdAt: LocalDateTime = LocalDateTime.now()
)