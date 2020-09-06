package com.xux.elib.controller.api

import com.xux.elib.service.FiletService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

@RestController
@RequestMapping("/api/filet/")
class FiletController(
        val filetService: FiletService
) {

    @PostMapping("local")
    fun createFromFileName(filename: String): Int {
        val file = File(filename)
        return filetService.add(file)
    }

    @PostMapping("upload")
    fun createFromUpload(file: MultipartFile): Int {
        TODO()
    }
}