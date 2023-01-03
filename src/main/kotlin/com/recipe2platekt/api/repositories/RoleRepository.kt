package com.recipe2platekt.api.repositories

import com.recipe2platekt.api.entities.AppRole
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<AppRole, Long>