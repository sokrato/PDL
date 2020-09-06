package com.xux.elib.jpa.repo

import com.xux.elib.jpa.entity.Category
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class JPACategoryRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val cateRepo: JPACategoryRepository
) {
    @Test
    fun smoke() {
        val cat = Category(name = "CS", rank = 1)
        entityManager.persist(cat)
        entityManager.flush()
        val found = cateRepo.findByIdOrNull(cat.id!!)
        assertThat(found).isEqualTo(cat)
    }
}