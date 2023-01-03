package com.recipe2platekt.api.http.request

import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull


data class LoginRequest(
    @NotNull
    @NotEmpty
    val email: String,

    @NotNull
    @NotEmpty
    val password: String,
)