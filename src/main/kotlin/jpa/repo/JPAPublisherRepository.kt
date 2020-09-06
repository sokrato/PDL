package com.xux.elib.jpa.repo

import com.xux.elib.jpa.entity.Publisher
import com.xux.elib.repo.PublisherRepo
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import javax.annotation.PostConstruct
import javax.transaction.Transactional

interface JPAPublisherRepository : CrudRepository<Publisher, Int> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Publisher SET id=?2 WHERE id=?1", nativeQuery = true)
    fun setId(oldId: Int, newId: Int)
}

fun Publisher.toModel(): com.xux.elib.model.Publisher {
    return com.xux.elib.model.Publisher(
            id ?: 0,
            shortName, fullName, country
    )
}

fun com.xux.elib.model.Publisher.toEntity(): Publisher {
    return Publisher(
            if (id > 0) id else null,
            shortName, fullName, country
    )
}

class JPAPublisherRepoImpl(
        val raw: JPAPublisherRepository
) : PublisherRepo {
    override fun add(publisher: com.xux.elib.model.Publisher): Int {
        val p = publisher.toEntity()
        return raw.save(p).id!!
    }

    override fun delete(id: Int) {
        raw.deleteById(id)
    }

    override fun update(publisher: com.xux.elib.model.Publisher) {
        raw.save(publisher.toEntity())
    }

    override fun findAll(): List<com.xux.elib.model.Publisher> {
        return raw.findAll().map { it.toModel() }.toList()
    }

    @PostConstruct
    fun init() {
        if (raw.existsById(Publisher.UnNamed.id!!))
            return
        val p = raw.save(Publisher.UnNamed)
        if (p.id != Publisher.UnNamed.id) {
            raw.setId(p.id!!, Publisher.UnNamed.id!!)
        }
    }
}