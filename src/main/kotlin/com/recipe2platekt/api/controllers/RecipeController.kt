package com.recipe2platekt.api.controllers

import com.recipe2platekt.api.entities.Recipe
import com.recipe2platekt.api.http.request.recipe.CreateRecipeFileRequest
import com.recipe2platekt.api.http.request.recipe.CreateRecipeRequest
import com.recipe2platekt.api.http.request.recipe.UpdateRecipeRequest
import com.recipe2platekt.api.http.response.recipe.RecipeWithPublisherAndCategoryResponse
import com.recipe2platekt.api.http.response.recipe.RecipeWithPublisherCategoryInstructionAndIngredientResponse
import com.recipe2platekt.api.services.RecipeService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/recipe")
class RecipeController(
    private val recipeService: RecipeService
) {

    @GetMapping("/")
    fun allRecipe(pageable: Pageable): ResponseEntity<Page<RecipeWithPublisherAndCategoryResponse>> {
        val recipes = recipeService.allRecipes(pageable)
        return ResponseEntity.ok(recipes)
    }

    @GetMapping("/search")
    fun searchRecipe(
        pageable: Pageable,
        @RequestParam term: String?,
        @RequestParam("category") categoryId: Long?
    ): ResponseEntity<Page<RecipeWithPublisherAndCategoryResponse>> {
        val recipes = recipeService.searchRecipe(
            pageable = pageable,
            term = term,
            categoryId = categoryId
        )
        return ResponseEntity.ok().body(recipes)
    }

    @GetMapping("/{recipeId}")
    fun findRecipe(@PathVariable recipeId: Long): ResponseEntity<RecipeWithPublisherCategoryInstructionAndIngredientResponse> {
        val recipe = recipeService.findRecipe(recipeId)
        return ResponseEntity.ok().body(recipe)
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addRecipe(
        @Valid
        @ModelAttribute createRecipeRequest: CreateRecipeRequest,
    ): ResponseEntity<Unit> {
        recipeService.addRecipe(createRecipeRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping("/{recipeId}/update")
    fun updateRecipe(
        @PathVariable recipeId: Long,
        @Valid
        @RequestBody updateRecipeRequest: UpdateRecipeRequest,
        @RequestPart image: MultipartFile,
        @RequestPart video: MultipartFile,
    ): ResponseEntity<Recipe> {
        val updatedRecipe = recipeService.updateRecipe(
            recipeId = recipeId,
            updateRecipeRequest = updateRecipeRequest,
            image = image,
            video = video
        )
        return ResponseEntity.accepted().body(updatedRecipe)
    }


    @DeleteMapping("/{recipeId}/delete")
    fun deleteRecipe(@PathVariable recipeId: Long): ResponseEntity<Any> {
        recipeService.deleteRecipe(recipeId)
        return ResponseEntity.noContent().build()
    }
}