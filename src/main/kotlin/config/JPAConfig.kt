package com.xux.elib.config

import com.xux.elib.helper.JSONHelper
import com.xux.elib.jpa.repo.*
import com.xux.elib.repo.CategoryRepo
import com.xux.elib.repo.FiletRepo
import com.xux.elib.repo.PostRepo
import com.xux.elib.repo.PublisherRepo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JPAConfig {
    @Bean
    fun filetRepo(raw: JPAFiletRepository): FiletRepo {
        return JPAFiletRepoImpl(raw)
    }

    @Bean
    fun categoryRepo(raw: JPACategoryRepository): CategoryRepo {
        return JPACategoryRepoImp(raw)
    }

    @Bean
    fun publisherRepo(raw: JPAPublisherRepository): PublisherRepo {
        return JPAPublisherRepoImpl(raw)
    }

    @Bean
    fun postRepo(postRaw: JPAPostRepository,
                 mappingRaw: JPAPostFiletMappingRepository,
                 jsonHelper: JSONHelper): PostRepo {
        return JPAPostRepoImpl(postRaw, mappingRaw, jsonHelper)
    }
}