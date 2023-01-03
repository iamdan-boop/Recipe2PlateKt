package com.recipe2platekt.api.entities

import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.Cascade
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.Date

@Entity
@Table(name = "recipes")
data class Recipe(
    @SequenceGenerator(
        name = "recipe_seq",
        sequenceName = "recipe_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        generator = "recipe_seq",
        strategy = GenerationType.SEQUENCE
    )
    @Id
    val id: Long? = null,
    val recipeName: String,
    val description: String,
    val imageUrl: String,
    val videoUrl: String,

    @OneToOne(fetch = FetchType.LAZY)
    val publisher: AppUser,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "recipes_categories",
        joinColumns = [JoinColumn(name = "recipe_id")],
        inverseJoinColumns = [JoinColumn(name = "categories_id")]
    )
    val categories: MutableList<Category> = mutableListOf(),

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "recipe_id")
    val ingredients: MutableList<Ingredient> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    val instructions: MutableList<Instruction> = mutableListOf(),

    @CreatedDate
    val createdAt: Date,

    @LastModifiedDate
    var updatedAt: Date
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Recipe

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , recipeName = $recipeName , description = $description , imageUrl = $imageUrl , videoUrl = $videoUrl , createdAt = $createdAt , updatedAt = $updatedAt )"
    }
}