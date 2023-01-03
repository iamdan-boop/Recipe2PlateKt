package com.recipe2platekt.api.mapper

import com.recipe2platekt.api.entities.Instruction
import com.recipe2platekt.api.http.request.instruction.CreateInstructionRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings


@Mapper(componentModel = "spring")
interface InstructionMapper {

    @Mappings(
        Mapping(target = "imageUrl", ignore = true),
        Mapping(target = "id", ignore = true),
        Mapping(target = "recipe", ignore = true)
    )
    fun toEntity(createInstructionRequest: CreateInstructionRequest): Instruction
}