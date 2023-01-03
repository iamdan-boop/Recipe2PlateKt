package com.recipe2platekt.api.mapper

import com.recipe2platekt.api.entities.Ingredient
import com.recipe2platekt.api.http.request.ingredient.CreateIngredientRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface IngredientMapper {

    @Mappings(
        Mapping(target = "imageUrl", ignore = true),
        Mapping(target = "id", ignore = true),
        Mapping(target = "recipe", ignore = true)
    )
    fun toEntity(createIngredientRequest: CreateIngredientRequest): Ingredient
}