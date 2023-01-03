package com.recipe2platekt.api.http.request.recipe

import com.recipe2platekt.api.http.request.ingredient.CreateIngredientRequest
import com.recipe2platekt.api.http.request.instruction.CreateInstructionRequest
import com.recipe2platekt.api.util.NoArg
import org.jetbrains.annotations.NotNull
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@NoArg
data class CreateRecipeRequest(
    val recipeName: String,
    val description: String,
    val image: MultipartFile,
    val video: MultipartFile,
//    val categories: MutableList<Int>,
//    val ingredients: MutableList<CreateIngredientRequest>,
    val instructions: MutableList<CreateInstructionRequest>,
)


@NoArg
data class CreateRecipeFileRequest(
    val image: MultipartFile,
    val video: MultipartFile,
    val ingredientsImage: List<MultipartFile>,
    val instructionsImage: List<MultipartFile>
)