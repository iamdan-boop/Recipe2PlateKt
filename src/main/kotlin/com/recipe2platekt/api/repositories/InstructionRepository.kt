package com.recipe2platekt.api.repositories

import com.recipe2platekt.api.entities.Instruction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InstructionRepository : JpaRepository<Instruction, Long> {

    @Query("SELECT instruction FROM Instruction instruction " +
            "LEFT JOIN FETCH instruction.recipe recipe " +
            "WHERE recipe.id = :recipeId")
    fun findAllByRecipe(recipeId: Long) : List<Instruction>
}