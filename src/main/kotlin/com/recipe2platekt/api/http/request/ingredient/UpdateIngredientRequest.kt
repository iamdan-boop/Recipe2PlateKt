package com.recipe2platekt.api.http.request.ingredient

import com.recipe2platekt.api.util.NoArg
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull
import org.springframework.web.multipart.MultipartFile

@NoArg
data class UpdateIngredientRequest(
    val step: Int,
    val ingredientName: String,
    val image: MultipartFile,
)