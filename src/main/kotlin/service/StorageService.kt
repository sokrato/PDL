package com.xux.elib.service

import com.xux.elib.HashHelper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths

interface StorageService {
    /**
     * Save a file
     * @return storage key, used later to fetch the file
     */
    fun save(file: File, rename: Boolean = false): String

    fun exists(key: String): Boolean

    fun fetch(key: String): File

    fun remove(key: String)
}

class HashConflict : RuntimeException("hash conflict")

private val puncts = Regex("\\W+")

fun String.removePunctuations(): String {
    return replace(puncts, "")
}

/**
 * 文件名后缀只为方便浏览文件时的阅览，并不影响存储，所以不要抛异常
 */
val String.filenameExtension: String
    get() {
        val index = lastIndexOf('.')
        if (index < 0)
            return ""

        val ext = substring(index).removePunctuations().toLowerCase()
        return if (ext.length > 8)
            ext.substring(0, 8)
        else
            ext
    }

internal val String.targetPath: String
    get() {
        val a = substring(0, 2)
        val b = substring(2, 4)
        val c = substring(4)
        return "$a/$b/$c"
    }

class LocalFileSystemStorage(
        private val root: Path,
        private val hashHelper: HashHelper
) : StorageService {

    constructor(rootDir: String, hashHelper: HashHelper) : this(Paths.get(rootDir), hashHelper)

    init {
        logger.info("Storage root dir: $root")
        val file = root.toFile()
        if (!file.exists()) {
            logger.warn("Creating storage root dir: $root")
            file.mkdirs()
        } else if (!file.isDirectory) {
            throw IllegalArgumentException("Storage root directory does not exist: $root")
        }
    }

    companion object {
        @JvmStatic
        val logger: Logger = LoggerFactory.getLogger(LocalFileSystemStorage::class.java)
    }

    override fun save(file: File, rename: Boolean): String {
        try {
            logger.info("saving file: name=${file.name} rename=$rename")
            val key = save0(file, rename)
            logger.info("saved file: name=${file.name} key=$key")
            return key
        } catch (ex: Exception) {
            logger.error("err saving file: name=${file.name} rename=$rename", ex)
            throw ex
        }
    }

    private fun save0(file: File, rename: Boolean): String {
        if (!file.exists())
            throw IllegalArgumentException("file does not exist")
        if (!file.isFile)
            throw IllegalArgumentException("can only save a file")
        val ext = file.name.filenameExtension
        val sha256 = file.inputStream().use { hashHelper.sha256ToString(it) }
        val key = "$sha256.$ext"

        val old = fetchOrNull(key)
        if (old != null && old.length() == file.length()) {
            throw HashConflict()
        }

        val target = root.resolve(key.targetPath)
        val parent = target.parent.toFile()
        if (parent.exists()) {
            if (!parent.isDirectory) {
                throw IOException("$parent is not a directory")
            }
        } else if (!parent.mkdirs()) {
            throw IOException("failed to mkdirs: $parent")
        }

        if (rename)
            file.renameTo(target.toFile())
        else
            file.copyTo(target.toFile())
        return key
    }

    private fun targetFile(key: String): File {
        if (key.length < 64)
            throw IllegalArgumentException("invalid key: $key")
        return root.resolve(key.targetPath).toFile()
    }

    override fun exists(key: String): Boolean {
        return targetFile(key).exists()
    }

    private fun fetchOrNull(key: String): File? {
        val target = targetFile(key)
        return if (target.exists()) target else null
    }

    override fun fetch(key: String): File {
        logger.info("fetching file: key=$key")
        val target = targetFile(key)
        if (target.exists()) {
            return target
        } else {
            logger.warn("invalid key: key=$key")
            throw IllegalArgumentException("invalid key")
        }
    }

    override fun remove(key: String) {
        logger.info("removing file: key=$key")
        try {
            val file = fetchOrNull(key)
            if (file != null && file.exists())
                file.delete()
        } catch (ex: Exception) {
            logger.error("err deleting file: key=$key", ex)
            throw ex
        }
    }
}
