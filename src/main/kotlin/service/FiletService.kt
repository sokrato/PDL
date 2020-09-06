package com.xux.elib.service

import com.google.common.base.Preconditions
import com.xux.elib.model.Filet
import com.xux.elib.repo.FiletRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime

interface FiletService {
    fun add(file: File): Int

    fun add(filet: Filet): Int

    fun delete(id: Int)

    fun update(filet: Filet)

    fun updateFile(filet: Filet, file: File)

    fun findById(filetId: Int): Filet?
}

@Service
class FiletServiceImpl(
    private val filetRepo: FiletRepo,
    private val storage: StorageService
) : FiletService {

    companion object {
        @JvmStatic
        val logger: Logger = LoggerFactory.getLogger(FiletServiceImpl::class.java)
    }

    override fun add(file: File): Int {
        logger.info("adding file: name=${file.name}")

        val size = file.length()
        val key = storage.save(file, true)
        val filet = Filet(
            name = file.name,
            size = size,
            format = file.name.filenameExtension,
            storeKey = key
        )
        return add(filet)
    }

    override fun add(filet: Filet): Int {
        // todo 2020.6.29: check input
        logger.info("adding filet: name=${filet.name} storeKey=${filet.storeKey}")
        try {
            val id = filetRepo.insert(filet)
            logger.info("added filet: name=${filet.name} storeKey=${filet.storeKey} id=$id")
            return id
        } catch (ex: Exception) {
            logger.error("err adding filet: name=${filet.name} storeKey=${filet.storeKey}", ex)
            throw ex
        }
    }

    override fun delete(id: Int) {
        logger.info("deleting filet: id=$id")
        val f = findById(id)
        if (f != null) {
            storage.remove(f.storeKey)
            filetRepo.delete(id)
        }
    }

    override fun update(filet: Filet) {
        // todo 2020.6.29: check input
        Preconditions.checkArgument(filet.id > 0)
        logger.info("updating filet: $filet")
        filetRepo.update(filet)
    }

    override fun updateFile(filet: Filet, file: File) {
        logger.info("updating file: filet=$filet file=${file.name}")
        try {
            filet.size = file.length()
            filet.storeKey = storage.save(file, true)
            filet.format = file.name.filenameExtension
            filet.createdAt = LocalDateTime.now()
        } catch (ex: Exception) {
            logger.error("err saving file: filetId=${filet.id}", ex)
            throw ex
        }

        update(filet)
    }

    override fun findById(filetId: Int): Filet? {
        return filetRepo.findById(filetId)
    }
}