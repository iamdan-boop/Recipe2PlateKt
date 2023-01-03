package com.recipe2platekt.api.mapper

import com.recipe2platekt.api.entities.Recipe
import com.recipe2platekt.api.http.request.recipe.CreateRecipeRequest
import com.recipe2platekt.api.http.response.recipe.RecipeWithPublisherAndCategoryResponse
import com.recipe2platekt.api.http.response.recipe.RecipeWithPublisherCategoryInstructionAndIngredientResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(
    componentModel = "spring", uses = [
        IngredientMapper::class,
        InstructionMapper::class,
    ]
)
interface RecipeMapper {

    @Mappings(
        Mapping(target = "categories", ignore = true),
        Mapping(target = "id", ignore = true),
        Mapping(target = "imageUrl", ignore = true),
        Mapping(target = "videoUrl", ignore = true),
        Mapping(target = "updatedAt", ignore = true),
        Mapping(target = "publisher", ignore = true),
        Mapping(target = "createdAt", ignore = true)
    )
    fun toRecipeEntity(createRecipeRequest: CreateRecipeRequest): Recipe

    fun toRecipeWithPublisherAndCategoryResponse(recipe: Recipe): RecipeWithPublisherAndCategoryResponse

    fun toRecipeWithPublisherCategoryInstructionAndIngredient(recipe: Recipe): RecipeWithPublisherCategoryInstructionAndIngredientResponse

}