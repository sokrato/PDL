package com.xux.elib.model

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Post下的文件都是同一个出版商在同一日期出版的
 */
data class Post(
    var id: Int = 0,
    var title: String = "",
    var slug: String = "",
    var author: String = "",
    var category: Category = Category.UnCategorized,
    var tags: Set<String> = emptySet(),
    var rating: Short = 0,
    var content: String = "",
    var filets: List<Filet> = emptyList(),
    var properties: Map<String, String> = emptyMap(),
    var publisher: Publisher = Publisher.UnNamed,
    var publishedAt: LocalDate = LocalDate.now(),
    var createdAt: LocalDateTime = LocalDateTime.now()
)
