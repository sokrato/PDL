package com.xux.elib.repo

import com.xux.elib.model.Publisher

interface PublisherRepo {
    fun add(publisher: Publisher): Int

    fun delete(id: Int)

    fun update(publisher: Publisher)

    fun findAll(): List<Publisher>
}