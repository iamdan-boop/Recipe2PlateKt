package com.recipe2platekt.api.repositories

import com.recipe2platekt.api.entities.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long> {

    @Query("SELECT appUser FROM AppUser appUser WHERE appUser.email = :email")
    fun findByEmail(email: String): Optional<AppUser>

    fun existsByEmail(email: String) : Boolean
}