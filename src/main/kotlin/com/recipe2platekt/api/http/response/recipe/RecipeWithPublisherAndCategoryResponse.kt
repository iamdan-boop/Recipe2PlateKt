package com.recipe2platekt.api.http.response.recipe

import com.recipe2platekt.api.entities.AppUser
import com.recipe2platekt.api.entities.Category

data class RecipeWithPublisherAndCategoryResponse(
    val id: Long,
    val recipeName: String,
    val description: String,
    val imageUrl: String,
    val videoUrl: String,
    val categories: Set<Category>,
    val publisher: AppUser
)