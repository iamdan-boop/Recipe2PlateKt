package com.recipe2platekt.api.http.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull

data class SignupRequest(
    @NotNull
    @NotEmpty
    val firstName: String,

    @NotNull
    @NotEmpty
    val lastName: String,

    @NotNull
    @Email
    val email: String,

    @NotNull
    @NotEmpty
    val password: String,
)