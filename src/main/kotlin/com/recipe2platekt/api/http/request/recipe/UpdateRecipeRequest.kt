package com.recipe2platekt.api.http.request.recipe

import com.recipe2platekt.api.util.NoArg
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull
import org.springframework.web.multipart.MultipartFile

@NoArg
data class UpdateRecipeRequest(
    val recipeName: String,
    val description: String,
)
