package com.recipe2platekt.api.services

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.recipe2platekt.api.exceptions.NotFoundException
import com.recipe2platekt.api.exceptions.ThirdPartyException
import org.hibernate.annotations.NotFound
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CloudinaryService(
    private val cloudinary: Cloudinary
) {

    @Throws(NotFoundException::class)
    fun uploadMedia(media: MultipartFile): String? {
        if (media.isEmpty) throw NotFoundException("File not found.")

        val uploadResult = cloudinary.uploader().upload(media.bytes, ObjectUtils.asMap("resource_type", "auto"))
            ?: throw ThirdPartyException("Couldn't upload file server error.")
        return uploadResult["source_url"] as String
    }
}