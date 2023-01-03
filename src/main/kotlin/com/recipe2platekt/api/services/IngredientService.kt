package com.recipe2platekt.api.services

import com.recipe2platekt.api.entities.Ingredient
import com.recipe2platekt.api.exceptions.NotFoundException
import com.recipe2platekt.api.http.request.ingredient.CreateIngredientRequest
import com.recipe2platekt.api.http.request.ingredient.UpdateIngredientRequest
import com.recipe2platekt.api.mapper.IngredientMapper
import com.recipe2platekt.api.repositories.IngredientRepository
import com.recipe2platekt.api.repositories.RecipeRepository
import org.hibernate.annotations.NotFound
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class IngredientService(
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientMapper: IngredientMapper,
    private val cloudinaryService: CloudinaryService,
) {

    fun allIngredientsByRecipe(recipeId: Long): List<Ingredient> {
        return ingredientRepository.findIngredientByRecipe(recipeId)
    }

    @Transactional
    fun addIngredient(
        recipeId: Long,
        createIngredientRequest: CreateIngredientRequest
    ): Ingredient {
        val foundRecipe = recipeRepository.findById(recipeId)
            .orElseThrow { NotFoundException("Recipe not found") }

        val ingredient = ingredientMapper.toEntity(createIngredientRequest)
            .copy(
//                imageUrl = createIngredientRequest. ?: "No Image"
            )
        foundRecipe.ingredients.add(ingredient)

        recipeRepository.save(foundRecipe)
        return ingredient
    }

    @Transactional
    @Modifying
    fun updateIngredient(
        recipeId: Long,
        ingredientId: Long,
        updateIngredientRequest: UpdateIngredientRequest
    ): Ingredient {
        val foundRecipe = recipeRepository.findById(recipeId)
            .orElseThrow { NotFoundException("Recipe not found.") }

        val checkIfIngredientExistsInRecipe = foundRecipe.ingredients.any { it.id!! == ingredientId }
        if (!checkIfIngredientExistsInRecipe) {
            throw NotFoundException("Ingredient doesn't seem to exists in this recipe")
        }

        val updatedIngredient = ingredientRepository.findById(ingredientId)
            .orElseThrow { NotFoundException("Ingredient not found.") }
            .copy(
                ingredientName = updateIngredientRequest.ingredientName,
                imageUrl = cloudinaryService.uploadMedia(updateIngredientRequest.image) ?: ""
            )

        return ingredientRepository.save(updatedIngredient)
    }


    fun deleteIngredient(recipeId: Long, ingredientId: Long) {
        val foundRecipe = recipeRepository.findById(recipeId)
            .orElseThrow { NotFoundException("Recipe not found") }

        recipeRepository.save(
            foundRecipe.copy(
                ingredients = foundRecipe.ingredients.filter { it.id!! != ingredientId }.toMutableList()
            )
        )
    }
}