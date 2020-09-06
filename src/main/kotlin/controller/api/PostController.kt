package com.xux.elib.controller.api

import com.xux.elib.model.Post
import com.xux.elib.service.FiletService
import com.xux.elib.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.transaction.Transactional

@RestController
@RequestMapping("/api/post/")
class PostController @Autowired constructor(
        private val postService: PostService,
        private val filetService: FiletService
) {
    @GetMapping("{id}")
    fun findById(@PathVariable id: Int): Post? {
        return postService.findById(id)
    }

    @PostMapping("local")
    fun createFromFileName(filename: String): Int {
        val file = File(filename)
        return createFromFile(file, Post())
    }

    @PostMapping("upload")
    fun createFromUpload(file: MultipartFile): Int {
        TODO()
    }

    @Transactional
    fun createFromFile(file: File, post: Post): Int {
        val filetId = filetService.add(file)
        if (post.title.isEmpty())
            post.title = file.name.let {
                val lastIndex = it.lastIndexOf(".")
                if (lastIndex > -1)
                    it.substring(0, lastIndex)
                else
                    it
            }
        val postId = postService.add(post)
        postService.addFilet(postId, filetId)
        return postId
    }

    @PostMapping("{id}")
    fun update(@PathVariable id: String) {
        //
    }

    @DeleteMapping("{id}")
    fun remove(@PathVariable id: String) {
        //
    }
}