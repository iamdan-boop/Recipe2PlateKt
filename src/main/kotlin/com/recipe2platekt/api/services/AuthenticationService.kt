package com.recipe2platekt.api.services

import com.recipe2platekt.api.entities.AppUser
import com.recipe2platekt.api.exceptions.AlreadyExistsException
import com.recipe2platekt.api.exceptions.NotFoundException
import com.recipe2platekt.api.http.request.LoginRequest
import com.recipe2platekt.api.http.request.SignupRequest
import com.recipe2platekt.api.http.response.AuthTokenResponse
import com.recipe2platekt.api.repositories.AppUserRepository
import com.recipe2platekt.api.repositories.RoleRepository
import com.recipe2platekt.api.util.JwtTokenUtil
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val appUserRepository: AppUserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtTokenUtil: JwtTokenUtil
) {

    fun signIn(authRequest: LoginRequest): AuthTokenResponse {
        val appUser = appUserRepository.findByEmail(authRequest.email)
            .orElseThrow { UsernameNotFoundException("Invalid Credentials") }

        if (!passwordEncoder.matches(authRequest.password, appUser.password)) {
            throw BadCredentialsException("Invalid Credentials")
        }
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            appUser,
            null,
            arrayListOf(SimpleGrantedAuthority(appUser.role.roleName))
        )

        return AuthTokenResponse(jwtTokenUtil.generateToken(appUser.email))
    }


    fun signUp(signupRequest: SignupRequest) : AuthTokenResponse {
        val emailAlreadyExists = appUserRepository.existsByEmail(signupRequest.email)

        if (emailAlreadyExists) throw AlreadyExistsException("Email already exists")
        val role = roleRepository.findById(2L).orElseThrow { NotFoundException("Role not found.") }

        val appUser = AppUser(
            firstName = signupRequest.firstName,
            lastName = signupRequest.lastName,
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            role = role
        )

        appUserRepository.save(appUser)
        return AuthTokenResponse(jwtTokenUtil.generateToken(appUser.email))
    }
}