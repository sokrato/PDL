package com.xux.elib.gui

import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.concurrent.Worker
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.web.WebView
import javafx.stage.Stage

class GuiFx : Application() {

    private lateinit var webView: WebView
    private lateinit var urlTextField: TextField

    private fun loadPage() {
        val url = urlTextField.text.trim().run {
            if (startsWith("http://"))
                this
            else
                "http://$this"
        }

        println("Loading URL: $url")

        val listener =
            ChangeListener { ob: ObservableValue<out Worker.State>, old: Worker.State, new: Worker.State ->
                println("$ob: $old -> $new")
                if (new == Worker.State.SUCCEEDED) {
                    val res = webView.engine.executeScript("location.href")
                    println("js result: $res")

                    webView.engine.executeScript("alert(location.href)")
                }
            }
        webView.engine.loadWorker.stateProperty()?.addListener(listener)
        webView.engine.load(url)
        webView.engine.onAlert = EventHandler {
            println("alerting: ${it.data}")
        }
    }

    override fun start(stage: Stage) {
        webView = WebView()
        val label = Label("URL")
        urlTextField = TextField("http://example.com").apply {
            minWidth = 120.0

            onKeyPressed = EventHandler {
                println("pressed: ${it.code}")
                if (it.code == KeyCode.ENTER) {
                    loadPage()
                    label.isVisible = false
                } else
                    label.isVisible = true
            }
        }

        val btn = Button("Load").also {
            it.onAction = EventHandler {
                loadPage()
            }
        }

        val root: Parent = VBox(
            10.0,
            HBox(10.0, label, urlTextField, btn),
            webView
        )

        stage.scene = Scene(root, 640.0, 480.0)
        stage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(GuiFx::class.java, *args)
}