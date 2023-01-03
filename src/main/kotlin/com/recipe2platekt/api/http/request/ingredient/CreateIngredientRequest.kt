package com.recipe2platekt.api.http.request.ingredient

import com.recipe2platekt.api.util.NoArg


@NoArg
data class CreateIngredientRequest(
    val ingredientName: String ,
)