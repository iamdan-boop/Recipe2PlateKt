package com.recipe2platekt.api.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "app_users")
data class AppUser(
    @SequenceGenerator(
        sequenceName = "app_user_seq",
        name = "app_user_seq",
        allocationSize = 1,
    )
    @GeneratedValue(
        generator = "app_user_seq",
        strategy = GenerationType.SEQUENCE
    )
    @Id
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    @JsonIgnore
    val password: String,
    @ManyToOne
    val role: AppRole
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as AppUser

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , firstName = $firstName , lastName = $lastName , email = $email , password = $password , role = $role )"
    }
}