package com.xux.elib.jpa.repo

import com.xux.elib.jpa.entity.Filet
import com.xux.elib.repo.FiletRepo
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

interface JPAFiletRepository : CrudRepository<Filet, Int>

fun com.xux.elib.model.Filet.toEntity(): Filet {
    return Filet(
        if (id > 0) id else null,
        name, size, format, storeKey, createdAt
    )
}

fun Filet.toModel(): com.xux.elib.model.Filet {
    return com.xux.elib.model.Filet(
        id ?: 0, name, size, format, storeKey, createdAt
    )
}

class JPAFiletRepoImpl (
    val raw: JPAFiletRepository
): FiletRepo {
    override fun insert(filet: com.xux.elib.model.Filet): Int {
        val f = filet.toEntity()
        return raw.save(f).id!!
    }

    override fun delete(id: Int) {
        raw.deleteById(id)
    }

    override fun update(filet: com.xux.elib.model.Filet) {
        raw.save(filet.toEntity())
    }

    override fun findById(id: Int): com.xux.elib.model.Filet? {
        return raw.findByIdOrNull(id)?.toModel()
    }
}