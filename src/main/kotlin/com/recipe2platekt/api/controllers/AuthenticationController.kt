package com.recipe2platekt.api.controllers

import com.recipe2platekt.api.http.request.LoginRequest
import com.recipe2platekt.api.http.request.SignupRequest
import com.recipe2platekt.api.http.response.AuthTokenResponse
import com.recipe2platekt.api.services.AuthenticationService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/signIn")
    fun signIn(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<AuthTokenResponse> {
        val authToken = authenticationService.signIn(loginRequest)
        return ResponseEntity.ok().body(authToken)
    }

    @PostMapping("/signUp")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest): ResponseEntity<AuthTokenResponse> {
        val authToken = authenticationService.signUp(signupRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(authToken)
    }
}