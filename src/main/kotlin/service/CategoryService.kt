package com.xux.elib.service

import com.xux.elib.model.Category
import com.xux.elib.repo.CategoryRepo
import org.springframework.stereotype.Service

interface CategoryService {
    fun add(category: Category): Int

    fun delete(id: Int)

    fun update(category: Category)

    fun findAll(): List<Category>

    fun findById(id: Int): Category?
}

@Service
class CategoryServiceImpl(private val repo: CategoryRepo) : CategoryService {
    override fun add(category: Category): Int {
        return repo.add(category)
    }

    override fun delete(id: Int) {
        repo.delete(id)
    }

    override fun update(category: Category) {
        repo.update(category)
    }

    override fun findAll(): List<Category> {
        return repo.findAll()
    }

    override fun findById(id: Int): Category? {
        return repo.findById(id)
    }
}