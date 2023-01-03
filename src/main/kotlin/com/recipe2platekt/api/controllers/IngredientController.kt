package com.recipe2platekt.api.controllers

import com.electronwill.nightconfig.core.conversion.Path
import com.recipe2platekt.api.entities.Ingredient
import com.recipe2platekt.api.http.request.ingredient.CreateIngredientRequest
import com.recipe2platekt.api.http.request.ingredient.UpdateIngredientRequest
import com.recipe2platekt.api.services.IngredientService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recipe/{recipeId}/ingredient/")
class IngredientController(
    private val ingredientService: IngredientService
) {

    @GetMapping("/")
    fun allIngredientsByRecipe(@PathVariable recipeId: Long): ResponseEntity<List<Ingredient>> {
        val ingredients = ingredientService.allIngredientsByRecipe(recipeId)
        return ResponseEntity.ok().body(ingredients)
    }


    @PostMapping("/create")
    fun addIngredient(
        @PathVariable recipeId: Long,
        @Valid @ModelAttribute createIngredientRequest: CreateIngredientRequest
    ): ResponseEntity<Ingredient> {
        val ingredient =
            ingredientService.addIngredient(recipeId = recipeId, createIngredientRequest = createIngredientRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredient)
    }


    @PutMapping("/{ingredientId}/update")
    fun updateIngredient(
        @PathVariable recipeId: Long,
        @PathVariable ingredientId: Long,
        @Valid @ModelAttribute updateIngredientRequest: UpdateIngredientRequest
    ): ResponseEntity<Ingredient> {
        val updatedIngredient = ingredientService.updateIngredient(
            recipeId = recipeId,
            ingredientId = ingredientId,
            updateIngredientRequest = updateIngredientRequest
        )

        return ResponseEntity.accepted().body(updatedIngredient)
    }


    @DeleteMapping("/{ingredientId}/delete")
    fun deleteIngredient(
        @PathVariable recipeId: Long,
        @PathVariable ingredientId: Long
    ): ResponseEntity<Any> {
        ingredientService.deleteIngredient(recipeId = recipeId, ingredientId = ingredientId)
        return ResponseEntity.noContent().build()
    }
}