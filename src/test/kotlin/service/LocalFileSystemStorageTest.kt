package service

import com.xux.elib.HashHelperImpl
import com.xux.elib.service.HashConflict
import com.xux.elib.service.LocalFileSystemStorage
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocalFileSystemStorageTest {
    private val root = Paths.get("/tmp/test-storage")
    private val hello = root.resolve("hello.txt").toFile()
    private val world = root.resolve("world.pdf").toFile()
    private val storage = LocalFileSystemStorage(root, HashHelperImpl())

    @BeforeAll
    fun init() {
        val rootFile = root.toFile()
        if (rootFile.exists())
            rootFile.deleteRecursively()

        rootFile.mkdirs()

        val msg = "hello world"
        root.resolve("b9").toFile().deleteRecursively()
        hello.writeText(msg)
        world.writeText(msg)
    }

    @Test
    fun save() {
        val key = storage.save(hello)
        assertTrue(root.resolve("hello.txt").toFile().exists())
        assertFalse(key.isEmpty())
        assertTrue(storage.exists(key))

        val file = storage.fetch(key)
        assertEquals(hello.length(), file.length())

        assertThrows<HashConflict> {
            storage.save(hello)
        }

        assertThrows<IllegalArgumentException> {
            storage.fetch("invalid key")
        }

        assertThrows<IllegalArgumentException> {
            storage.fetch("no-such-key-$key")
        }

        val k2 = storage.save(world, rename = true)
        assertFalse(root.resolve("world.pdf").toFile().exists())

        storage.remove(key)
        assertFalse(storage.exists(key))
        storage.remove(k2)
        assertFalse(storage.exists(k2))
        // removing a non-existent file is OK
        storage.remove(k2)
    }
}