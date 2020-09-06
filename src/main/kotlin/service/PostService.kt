package com.xux.elib.service

import com.xux.elib.model.Post
import com.xux.elib.repo.PostRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


interface PostService {
    fun add(post: Post): Int

    fun delete(id: Int)

    fun update(post: Post)

    fun addFilet(postId: Int, filetId: Int, rank: Int = -1)

    fun setFilets(postId: Int, filetIds: List<Int>)

    fun removeAllFilets(postId: Int)

    fun removeFilet(postId: Int, filetId: Int)

    fun findById(postId: Int): Post?

    fun findAllByCategory(catId: Int): List<Post>

    fun findAllByTag(tag: String): List<Post>

    fun findAllByMultipleTags(tags: Set<String>): List<Post>

    fun findAllByPublisher(publisherId: Int): List<Post>
}

@Service
class PostServiceImpl(
        private val repo: PostRepo,
        private val filetService: FiletService
) : PostService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PostServiceImpl::class.java)
    }

    override fun add(post: Post): Int {
        // check input
        logger.info("adding post: $post")
        try {
            return repo.insert(post)
        } catch (ex: Exception) {
            logger.error("err adding post: $post", ex)
            throw ex
        }
    }

    override fun delete(id: Int) {
        logger.info("deleting post: id=$id")
        try {
            repo.delete(id)
        } catch (ex: Exception) {
            logger.error("err deleting post: id=$id", ex)
            throw ex
        }
    }

    override fun update(post: Post) {
        logger.info("updating post: $post")
        try {
            repo.update(post)
        } catch (ex: Exception) {
            logger.error("err updating post: $post", ex)
            throw ex
        }
    }

    override fun addFilet(postId: Int, filetId: Int, rank: Int) {
        repo.addFilet(postId, filetId, rank)
    }

    override fun setFilets(postId: Int, filetIds: List<Int>) {
        repo.setFilets(postId, filetIds)
    }

    override fun removeAllFilets(postId: Int) {
        repo.removeAllFilets(postId)
    }

    override fun removeFilet(postId: Int, filetId: Int) {
        repo.removeFilet(postId, filetId)
    }

    override fun findById(postId: Int): Post? {
        if (postId < 1)
            throw IllegalArgumentException("illegal postId")

        val post = repo.findById(postId)
        if (post != null) {
            post.filets = repo.findAllFiletIds(postId).map {
                filetService.findById(it)!!
            }.toList()
        }
        return post
    }

    override fun findAllByCategory(catId: Int): List<Post> {
        return repo.findAllByCategory(catId)
    }

    override fun findAllByTag(tag: String): List<Post> {
        return repo.findAllByTag(tag)
    }

    override fun findAllByMultipleTags(tags: Set<String>): List<Post> {
        return repo.findAllByMultipleTags(tags)
    }

    override fun findAllByPublisher(publisherId: Int): List<Post> {
        return repo.findAllByPublisher(publisherId)
    }
}