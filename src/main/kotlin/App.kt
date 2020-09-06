package com.xux.elib

import com.xux.elib.cli.TerminalApp
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class App

fun main(args: Array<String>) {
    val context = runApplication<App>(*args)
    var shell = false
    for (arg in args) {
        if ("--shell".equals(arg)) {
            shell = true
            break
        }
    }
    if (shell) {
        val bean = context.getBean(TerminalApp::class.java)
        bean.start()
    }
}
