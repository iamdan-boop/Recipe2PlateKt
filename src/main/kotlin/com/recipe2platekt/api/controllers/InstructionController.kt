package com.recipe2platekt.api.controllers

import com.recipe2platekt.api.entities.Instruction
import com.recipe2platekt.api.http.request.instruction.CreateInstructionRequest
import com.recipe2platekt.api.services.InstructionService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recipe/{recipeId}/instruction")
class InstructionController(
    private val instructionService: InstructionService
) {

    @GetMapping("/")
    fun allInstructionsByRecipe(@PathVariable recipeId: Long): ResponseEntity<List<Instruction>> {
        val instructions = instructionService.allInstructionsByRecipe(recipeId)
        return ResponseEntity.ok().body(instructions)
    }

    @PostMapping("/create")
    fun addInstruction(
        @PathVariable recipeId: Long,
        @Valid @ModelAttribute createInstructionRequest: CreateInstructionRequest
    ): ResponseEntity<Instruction> {
        val instruction = instructionService.addInstruction(
            recipeId = recipeId,
            createInstructionRequest = createInstructionRequest
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(instruction)
    }
}