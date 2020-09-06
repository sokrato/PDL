package com.xux.elib.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.xux.elib.HashHelper
import com.xux.elib.HashHelperImpl
import com.xux.elib.cli.TerminalApp
import com.xux.elib.helper.JSONHelper
import com.xux.elib.helper.JSONHelperImpl
import com.xux.elib.service.LocalFileSystemStorage
import com.xux.elib.service.StorageService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import toolkit.nrepl.NReplService
import toolkit.nrepl.NReplServiceImpl
import javax.sql.DataSource

@Configuration
class MainConfig {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MainConfig::class.java)
    }

    // @Bean
    fun dataSource(
            @Value("\${spring.datasource.url}") url: String,
            @Value("\${spring.datasource.datasource-class-name}") dataSourceClassName: String,
            @Value("\${spring.datasource.username}") username: String,
            @Value("\${spring.datasource.password}") password: String
    ): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.dataSourceClassName = dataSourceClassName
        hikariConfig.jdbcUrl = url
        hikariConfig.username = username
        hikariConfig.password = password
        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun objectMapper() = ObjectMapper()

    @Bean
    fun jsonHelper(objectMapper: ObjectMapper): JSONHelper = JSONHelperImpl(objectMapper)

    @Bean
    fun hashHelper(): HashHelper {
        return HashHelperImpl()
    }

    @Bean
    fun storageService(
            @Value("\${elib.storage.root}")
            root: String,
            hashHelper: HashHelper
    ): StorageService {
        return LocalFileSystemStorage(root, hashHelper)
    }

    @Bean
    fun nreplService(
            @Value("\${nrepl.bind:}")
            bind: String,
            @Value("\${nrepl.port:0}")
            port: Int,
            context: ApplicationContext
    ): NReplService {
        val repl = NReplServiceImpl()
        repl.updateContext("context", context)
        repl.start(bind, port)
        return repl
    }

    @Bean
    fun terminalApp(context: ApplicationContext): TerminalApp {
        return TerminalApp(context)
    }
}