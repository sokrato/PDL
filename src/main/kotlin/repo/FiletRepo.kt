package com.xux.elib.repo

import com.xux.elib.model.Filet

interface FiletRepo {
    fun insert(filet: Filet): Int

    fun delete(id: Int)

    fun update(filet: Filet)

    fun findById(id: Int): Filet?
}
