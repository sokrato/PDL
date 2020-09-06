package com.xux.elib.repo

import com.xux.elib.model.Filet
import com.xux.elib.model.Post

interface PostRepo {
    fun insert(post: Post): Int

    fun delete(id: Int)

    fun update(post: Post)

    fun findAllFiletIds(postId: Int): List<Int>

    fun addFilet(postId: Int, filetId: Int, rank: Int = -1)

    fun setFilets(postId: Int, filetIds: List<Int>)

    fun removeFilet(postId: Int, filetId: Int)

    fun removeAllFilets(postId: Int)

    fun findById(postId: Int): Post?

    fun findAllByCategory(catId: Int): List<Post>

    fun findAllByTag(tag: String): List<Post>

    fun findAllByMultipleTags(tags: Set<String>): List<Post>

    fun findAllByPublisher(publisherId: Int): List<Post>
}
