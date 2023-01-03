package com.recipe2platekt.api.http.response.recipe

import com.recipe2platekt.api.entities.AppUser
import com.recipe2platekt.api.entities.Category
import com.recipe2platekt.api.entities.Instruction

data class RecipeWithPublisherCategoryInstructionAndIngredientResponse(
    val id: Long,
    val recipeName: String,
    val description: String,
    val imageUrl: String,
    val videoUrl: String,
    val categories: List<Category>,
    val instructions: List<Instruction>,
    val publisher: AppUser,
)