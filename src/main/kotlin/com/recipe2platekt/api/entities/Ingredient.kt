package com.recipe2platekt.api.entities

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "ingredients")
data class Ingredient(
    @SequenceGenerator(
        name = "ingredient_seq",
        sequenceName = "ingredient_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        generator = "ingredient_seq",
        strategy = GenerationType.SEQUENCE
    )
    @Id
    val id: Long? = null,
    val ingredientName: String,
    val imageUrl: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val recipe: Recipe
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Ingredient

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , ingredientName = $ingredientName )"
    }
}