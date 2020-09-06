package service

import com.xux.elib.service.filenameExtension
import com.xux.elib.service.removePunctuations
import kotlin.test.Test
import kotlin.test.assertEquals

class StorageServiceKtTest {

    @Test
    fun getFilenameExtension() {
        listOf(
            "a.PDF", "/a.pdf", "./a.pdf", "/a/b/a.pdf#"
        ).forEach { assertEquals("pdf", it.filenameExtension) }

        listOf(
            "", "abc", "abc."
        ).forEach {
            assertEquals("", it.filenameExtension)
        }

        assertEquals("abcdefgh", ".abcdefghij".filenameExtension)
    }

    @Test
    fun regex() {
        assertEquals(
            "",
            "+-*/!@#$%^&*()[]|/{};':,.<>?".removePunctuations()
        )
    }
}