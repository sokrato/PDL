package com.xux.elib.repo

import com.xux.elib.model.Category

interface CategoryRepo {
    fun add(category: Category): Int

    fun delete(id: Int)

    fun update(category: Category)

    fun findAll(): List<Category>

    fun findById(id: Int): Category?
}
