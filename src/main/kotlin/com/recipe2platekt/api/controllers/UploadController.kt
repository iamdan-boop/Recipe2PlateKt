package com.recipe2platekt.api.controllers

import com.recipe2platekt.api.services.CloudinaryService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class UploadController(
    private val cloudinaryService: CloudinaryService
) {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestPart file: MultipartFile): ResponseEntity<String> {
        val imageUrl = cloudinaryService.uploadMedia(file)
        return ResponseEntity.ok().body(imageUrl)
    }
}