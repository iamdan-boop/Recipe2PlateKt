package com.recipe2platekt.api.repositories

import com.recipe2platekt.api.entities.Category
import com.recipe2platekt.api.entities.Recipe
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecipeRepository : PagingAndSortingRepository<Recipe, Long>, JpaRepository<Recipe, Long> {

    @Query(
        "SELECT recipe FROM Recipe recipe " +
                "JOIN FETCH recipe.categories categories " +
                "WHERE recipe.recipeName LIKE :recipeName " +
                "AND categories.id IN(:categories)",
        countQuery = "SELECT COUNT(recipes) FROM Recipe recipes"
    )
    fun findByRecipeNameContainingIgnoreCaseAndCategoriesIn(
        pageable: Pageable,
        recipeName: String,
        categories: List<Long>
    ): Page<Recipe>

    @Query(
        "SELECT recipe FROM Recipe recipe " +
                "JOIN FETCH recipe.categories categories " +
                "JOIN FETCH recipe.publisher " +
                "JOIN FETCH recipe.instructions " +
                "JOIN FETCH recipe.ingredients",
    )
    fun findByIdWithRelations(id: Long): Recipe?

}