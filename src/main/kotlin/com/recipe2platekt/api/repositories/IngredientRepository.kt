package com.recipe2platekt.api.repositories

import com.recipe2platekt.api.entities.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IngredientRepository : JpaRepository<Ingredient, Long> {

    @Query("SELECT ingredient FROM Ingredient ingredient WHERE ingredient.recipe.id = :recipeId")
    fun findIngredientByRecipe(recipeId: Long) : List<Ingredient>
}