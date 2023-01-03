package com.recipe2platekt.api.entities

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "instructions")
data class Instruction(
    @SequenceGenerator(
        name = "instruction_seq",
        sequenceName = "instruction_seq",
        allocationSize = 1,
    )
    @GeneratedValue(
        generator = "instruction_seq",
        strategy = GenerationType.SEQUENCE
    )
    @Id
    val id: Long? = null,
    val step: Int,
    val instruction: String,
    val imageUrl: String,

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    val recipe: Recipe
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Instruction

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , step = $step , instruction = $instruction , imageUrl = $imageUrl , recipe = $recipe )"
    }
}