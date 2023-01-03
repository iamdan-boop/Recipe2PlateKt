package com.recipe2platekt.api.entities

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "categories")
data class Category(
    @SequenceGenerator(
        sequenceName = "category_seq",
        name = "category_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        generator = "category_seq",
        strategy = GenerationType.SEQUENCE
    )
    @Id
    val id: Long? = null,
    val categoryName: String,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val recipes: MutableSet<Recipe> = mutableSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Category

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , categoryName = $categoryName )"
    }
}