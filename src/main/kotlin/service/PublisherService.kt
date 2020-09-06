package com.xux.elib.service

import com.xux.elib.model.Publisher
import com.xux.elib.repo.PublisherRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

interface PublisherService {
    fun add(publisher: Publisher): Int

    fun delete(id: Int)

    fun update(publisher: Publisher)

    fun findAll(): List<Publisher>
}

@Service
class PublisherServiceImpl(
        private val repo: PublisherRepo
) : PublisherService {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PublisherServiceImpl::class.java)
    }

    override fun add(publisher: Publisher): Int {
        logger.info("adding publisher: $publisher")
        return repo.add(publisher)
    }

    override fun delete(id: Int) {
        logger.info("deleting publisher: $id")
        repo.delete(id)
    }

    override fun update(publisher: Publisher) {
        logger.info("updating publisher: $publisher")
        repo.update(publisher)
    }

    override fun findAll(): List<Publisher> {
        return repo.findAll()
    }
}