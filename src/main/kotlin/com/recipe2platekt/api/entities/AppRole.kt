package com.recipe2platekt.api.entities

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "roles")
data class AppRole(
    @SequenceGenerator(
        name = "role_id_seq",
        sequenceName = "role_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        generator = "app_role_seq",
        strategy = GenerationType.SEQUENCE
    )
    @Id
    val id: Long? = null,
    val roleName: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AppRole

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , roleName = $roleName )"
    }
}