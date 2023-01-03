package com.recipe2platekt.api.config

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class CloudinaryConfig {

    @Value("\${cloudinary.api_key}")
    private val apiKey: String? = null

    @Value("\${cloudinary.cloud_name}")
    private val cloudName: String? = null

    @Value("\${cloudinary.api_secret}")
    private val apiSecret: String? = null

    @Bean
    fun cloudinary(): Cloudinary {
        return Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
            )
        )
    }
}