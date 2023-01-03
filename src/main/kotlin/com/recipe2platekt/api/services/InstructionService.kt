package com.recipe2platekt.api.services

import com.recipe2platekt.api.entities.Instruction
import com.recipe2platekt.api.exceptions.NotFoundException
import com.recipe2platekt.api.http.request.instruction.CreateInstructionRequest
import com.recipe2platekt.api.mapper.InstructionMapper
import com.recipe2platekt.api.repositories.InstructionRepository
import com.recipe2platekt.api.repositories.RecipeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InstructionService(
    private val instructionRepository: InstructionRepository,
    private val recipeRepository: RecipeRepository,
    private val instructionMapper: InstructionMapper,
    private val cloudinaryService: CloudinaryService
) {

    fun allInstructionsByRecipe(recipeId: Long): List<Instruction> =
        instructionRepository.findAllByRecipe(recipeId)

    @Transactional
    fun addInstruction(
        recipeId: Long,
        createInstructionRequest: CreateInstructionRequest
    ): Instruction {
        val foundRecipe = recipeRepository.findById(recipeId)
            .orElseThrow { NotFoundException("Recipe not found.") }

        val instruction = instructionMapper.toEntity(createInstructionRequest)
            .copy(
//                imageUrl = createInstructionRequest.imageUrl ?: "No Image"
            )
        foundRecipe.instructions.add(instruction)

        recipeRepository.save(foundRecipe)
        return instruction
    }

}