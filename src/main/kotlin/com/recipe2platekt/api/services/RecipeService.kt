package com.recipe2platekt.api.services

import com.cloudinary.utils.StringUtils
import com.recipe2platekt.api.entities.AppUser
import com.recipe2platekt.api.entities.Recipe
import com.recipe2platekt.api.exceptions.NotFoundException
import com.recipe2platekt.api.http.request.recipe.CreateRecipeRequest
import com.recipe2platekt.api.http.request.recipe.UpdateRecipeRequest
import com.recipe2platekt.api.http.response.recipe.RecipeWithPublisherAndCategoryResponse
import com.recipe2platekt.api.http.response.recipe.RecipeWithPublisherCategoryInstructionAndIngredientResponse
import com.recipe2platekt.api.mapper.RecipeMapper
import com.recipe2platekt.api.repositories.CategoryRepository
import com.recipe2platekt.api.repositories.RecipeRepository
import org.hibernate.annotations.NotFound
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.recipe2platekt.api.util.empty
import org.springframework.web.multipart.MultipartFile

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository,
    private val cloudinaryService: CloudinaryService,
    private val recipeMapper: RecipeMapper,
) {

    fun allRecipes(pageable: Pageable): Page<RecipeWithPublisherAndCategoryResponse> {
        return recipeRepository.findAll(pageable)
            .map { recipeMapper.toRecipeWithPublisherAndCategoryResponse(it) }
    }


    fun searchRecipe(
        pageable: Pageable,
        term: String?,
        categoryId: Long?
    ): Page<RecipeWithPublisherAndCategoryResponse> {
        if (term.isNullOrEmpty() || term.isBlank()) {
            return Page.empty()
        }

        val categories = mutableListOf<Long>()
        categoryId?.let {
            val foundCategory = categoryRepository.findById(it)
                .orElseThrow { NotFoundException("Category not found") }
            return@let categories.add(foundCategory.id!!)
        }

        return recipeRepository.findByRecipeNameContainingIgnoreCaseAndCategoriesIn(
            pageable = pageable,
            recipeName = term,
            categories = categories
        ).map { recipe -> recipeMapper.toRecipeWithPublisherAndCategoryResponse(recipe) }
    }


    fun findRecipe(recipeId: Long): RecipeWithPublisherCategoryInstructionAndIngredientResponse {
        val recipe = recipeRepository.findById(recipeId)
            .orElseThrow { NotFoundException("Recipe not found.") }
        return recipeMapper.toRecipeWithPublisherCategoryInstructionAndIngredient(recipe)
    }


    @Transactional
    fun addRecipe(createRecipeRequest: CreateRecipeRequest): Unit {
        println(createRecipeRequest)
//        val recipe = recipeMapper.toRecipeEntity(createRecipeRequest)
//            .copy(
//                publisher = SecurityContextHolder.getContext().authentication.principal as AppUser,
//                imageUrl = cloudinaryService.uploadMedia(createRecipeRequest.image!!) ?: "",
//                videoUrl = cloudinaryService.uploadMedia(createRecipeRequest.video!!) ?: "",
//            )
//
//        println("categories here")
//
////        recipe.categories.addAll(categoryRepository.findAllById(createRecipeRequest.categories ?: emptyList()))
//        recipeRepository.save(recipe)
    }


    @Transactional
    fun updateRecipe(
        recipeId: Long,
        updateRecipeRequest: UpdateRecipeRequest,
        image: MultipartFile,
        video: MultipartFile
    ): Recipe {
        val findRecipe = recipeRepository.findById(recipeId)
            .orElseThrow { NotFoundException("Recipe not found.") }
            .copy(
                recipeName = updateRecipeRequest.recipeName,
                description = updateRecipeRequest.description
            )

        val imageUrl = if (!image.isEmpty) {
            cloudinaryService.uploadMedia(image) ?: StringUtils.EMPTY
        } else findRecipe.imageUrl


        val videoUrl = if (video.isEmpty) {
            cloudinaryService.uploadMedia(video) ?: StringUtils.EMPTY
        } else findRecipe.videoUrl

        return recipeRepository.save(
            findRecipe.copy(
                imageUrl = imageUrl,
                videoUrl = videoUrl,
            )
        )
    }


    fun deleteRecipe(recipeId: Long): Unit {
        val recipe = recipeRepository.findById(recipeId)
            .orElseThrow { NotFoundException("Recipe not found.") }
        recipeRepository.delete(recipe)
    }
}