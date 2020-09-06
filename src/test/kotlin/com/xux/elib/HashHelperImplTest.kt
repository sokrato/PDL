package com.xux.elib

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * https://junit.org/junit5/docs/current/user-guide/
 */
class HashHelperImplTest {
    val hasher = HashHelperImpl()

    @Test
    fun sha256() {
        val cases = mapOf(
                "" to "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                "hello" to "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
                "world" to "486ea46224d1bb4fb680f34f7c9ad96a8f24ec88be73ea8e5a6c65260e9cb8a7"
        )
        for (case in cases) {
            val output = hasher.hexEncode(hasher.sha256ToBytes(case.key))
            assertEquals(case.value, output)

            val output2 = hasher.sha256ToString(case.key)
            assertEquals(case.value, output2)
        }
    }
}