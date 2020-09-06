package com.xux.elib.cli

import clojure.java.api.Clojure
import org.springframework.context.ApplicationContext

class TerminalApp (
        val ctx: ApplicationContext
) {

    companion object {
        var instance: TerminalApp? = null
    }

    fun start() {
        instance = this
        val fn = Clojure.`var`("clojure.main", "main")
        fn.invoke()
    }
}