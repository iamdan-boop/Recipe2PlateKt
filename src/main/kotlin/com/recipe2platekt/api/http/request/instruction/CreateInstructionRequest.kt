package com.recipe2platekt.api.http.request.instruction

import com.recipe2platekt.api.util.NoArg
import org.springframework.web.multipart.MultipartFile


@NoArg
data class CreateInstructionRequest(
    // val step: Int,
    val instruction: String,
    val image: MultipartFile
)