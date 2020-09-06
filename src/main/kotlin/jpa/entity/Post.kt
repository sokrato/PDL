package com.xux.elib.jpa.entity

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
// @Table(name = "post")
class Post(
        @Id @GeneratedValue
        var id: Int? = null,
        @Column(length = 100)
        var title: String = "",
        @Column(length = 100)
        var slug: String = "",
        var author: String = "",
        @ManyToOne
        var category: Category = Category.UnCategorized,
        var tags: String = "",
        var rating: Short = 0,
        @Lob
        var content: String = "",
        @Lob
        var properties: String = "",
        @ManyToOne
        var publisher: Publisher = Publisher.UnNamed,
        var publishedAt: LocalDate = LocalDate.now(),
        var createdAt: LocalDateTime = LocalDateTime.now()
)

@Entity
class PostFiletMapping(
        @Id @GeneratedValue
        var id: Int? = null,
        @ManyToOne
        var post: Post = Post(),
        @ManyToOne
        var filet: Filet = Filet(),
        var rank: Int = 0
)