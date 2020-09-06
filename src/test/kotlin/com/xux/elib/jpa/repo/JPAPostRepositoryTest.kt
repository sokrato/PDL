package com.xux.elib.jpa.repo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import kotlin.test.Test

@DataJpaTest
class JPAPostRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager
) {
    @Test
    fun smoke() {
    }
}