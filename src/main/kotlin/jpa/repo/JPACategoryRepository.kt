package com.xux.elib.jpa.repo

import com.xux.elib.jpa.entity.Category
import com.xux.elib.repo.CategoryRepo
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import javax.annotation.PostConstruct
import javax.transaction.Transactional

interface JPACategoryRepository : CrudRepository<Category, Int> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Category SET id = ?2 WHERE id=?1")
    fun setId(oldId: Int, newId: Int)
}

fun com.xux.elib.model.Category.toEntity(): Category {
    return Category(
            if (id > 0) id else null,
            name,
            rank,
            parentId
    )
}

fun Category.toModel(): com.xux.elib.model.Category {
    return com.xux.elib.model.Category(
            id ?: 0,
            name,
            rank,
            parentId
    )
}

class JPACategoryRepoImp(
        val raw: JPACategoryRepository
) : CategoryRepo {
    override fun add(category: com.xux.elib.model.Category): Int {
        val cat = category.toEntity()
        return raw.save(cat).id!!
    }

    override fun delete(id: Int) {
        raw.deleteById(id)
    }

    override fun update(category: com.xux.elib.model.Category) {
        if (category.id <= 0)
            throw IllegalArgumentException("id missing")
        val cat = category.toEntity()
        raw.save(cat)
    }

    override fun findAll(): List<com.xux.elib.model.Category> {
        return raw.findAll().map { it.toModel() }.toList()
    }

    override fun findById(id: Int): com.xux.elib.model.Category? {
        return raw.findByIdOrNull(id)?.toModel()
    }

    @PostConstruct
    fun init() {
        val id = Category.UnCategorized.id!!
        if (raw.existsById(id))
            return
        val new = raw.save(Category.UnCategorized)
        if (new.id != id) {
            raw.setId(new.id!!, id)
        }
    }
}