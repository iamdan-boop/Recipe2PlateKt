package com.recipe2platekt.api.repositories

import com.recipe2platekt.api.entities.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>