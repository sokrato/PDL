package com.xux.elib.jpa.repo

import com.xux.elib.helper.JSONHelper
import com.xux.elib.jpa.entity.Filet
import com.xux.elib.jpa.entity.Post
import com.xux.elib.jpa.entity.PostFiletMapping
import com.xux.elib.repo.PostRepo
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.findByIdOrNull
import javax.transaction.Transactional

interface JPAPostRepository : PagingAndSortingRepository<Post, Int> {
    fun findAllByCategoryId(categoryId: Int): List<Post>
    fun findAllByPublisherId(publisherId: Int): List<Post>
    fun findAllByTagsLike(tag: String): List<Post>
}

interface JPAPostFiletMappingRepository : CrudRepository<PostFiletMapping, Int> {

    fun countByPostId(postId: Int): Int

    fun findByPostIdAndFiletId(postId: Int, filetId: Int): PostFiletMapping?

    @Query(value = "SELECT filet_id, rank FROM Post_Filet_Mapping WHERE post_id = ?1 ORDER BY rank",
            nativeQuery = true)
    fun findAllByPostId(postId: Int): List<Int>

    @Modifying
    @Query(value = "DELETE FROM Post_Filet_Mapping WHERE post_id=?1 AND filet_id=?2",
            nativeQuery = true)
    fun deleteMapping(postId: Int, filetId: Int)

    fun deleteByPostId(postId: Int)
}

fun Set<String>.joinSorted(sep: String): String {
    return sorted().joinToString(sep)
}

fun com.xux.elib.model.Post.toEntity(jsonHelper: JSONHelper): Post {
    return Post(
            if (id > 0) id else null,
            title, slug, author, category.toEntity(), tags.joinSorted(","),
            rating, content, jsonHelper.dump(properties), publisher.toEntity(),
            publishedAt, createdAt
    )
}

fun Post.toModel(jsonHelper: JSONHelper): com.xux.elib.model.Post {
    return com.xux.elib.model.Post(
            id ?: 0,
            title, slug, author, category.toModel(), tags.split(",").toSet(),
            rating, content, emptyList(), jsonHelper.parseMap(properties), publisher.toModel(),
            publishedAt, createdAt
    )
}

open class JPAPostRepoImpl constructor(
        private val postRaw: JPAPostRepository,
        private val mappingRaw: JPAPostFiletMappingRepository,
        private val jsonHelper: JSONHelper
) : PostRepo {
    override fun insert(post: com.xux.elib.model.Post): Int {
        val p = post.toEntity(jsonHelper)
        return postRaw.save(p).id!!
    }

    @Transactional
    override fun delete(id: Int) {
        postRaw.deleteById(id)
        removeAllFilets(id)
    }

    override fun update(post: com.xux.elib.model.Post) {
        postRaw.save(post.toEntity(jsonHelper))
    }

    override fun findAllFiletIds(postId: Int): List<Int> {
        return mappingRaw.findAllByPostId(postId)
    }

    override fun addFilet(postId: Int, filetId: Int, rank: Int) {
        val rank0 = if (rank >= 0) rank else mappingRaw.countByPostId(postId)
        mappingRaw.save(mappingFromId(postId, filetId, rank0))
    }

    @Transactional
    override fun setFilets(postId: Int, filetIds: List<Int>) {
        var seq = 0
        for (fid in filetIds) {
            val row = mappingRaw.findByPostIdAndFiletId(postId, fid)
            if (row == null) {
                mappingRaw.save(mappingFromId(postId, fid, seq))
            } else {
                row.rank = seq
                mappingRaw.save(row)
            }
            seq += 1
        }
    }

    private fun mappingFromId(postId: Int, filetId: Int, rank: Int): PostFiletMapping {
        return PostFiletMapping(
                post = Post(id = postId),
                filet = Filet(id = filetId),
                rank = rank)
    }

    override fun removeFilet(postId: Int, filetId: Int) {
        mappingRaw.deleteMapping(postId, filetId)
    }

    @Transactional
    override fun removeAllFilets(postId: Int) {
        mappingRaw.deleteByPostId(postId)
    }

    override fun findById(postId: Int): com.xux.elib.model.Post? {
        return postRaw.findByIdOrNull(postId)?.toModel(jsonHelper)
    }

    override fun findAllByCategory(catId: Int): List<com.xux.elib.model.Post> {
        return postRaw.findAllByCategoryId(catId).toModelList()
    }

    override fun findAllByTag(tag: String): List<com.xux.elib.model.Post> {
        TODO("Not yet implemented")
    }

    override fun findAllByMultipleTags(tags: Set<String>): List<com.xux.elib.model.Post> {
        TODO("Not yet implemented")
    }

    override fun findAllByPublisher(publisherId: Int): List<com.xux.elib.model.Post> {
        return postRaw.findAllByPublisherId(publisherId).toModelList()
    }

    private fun List<Post>.toModelList(): List<com.xux.elib.model.Post> {
        return map { it.toModel(jsonHelper) }.toList()
    }
}