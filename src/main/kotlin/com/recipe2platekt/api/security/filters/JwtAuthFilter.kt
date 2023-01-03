package com.recipe2platekt.api.security.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.recipe2platekt.api.entities.AppUser
import com.recipe2platekt.api.http.response.ErrorDto
import com.recipe2platekt.api.repositories.AppUserRepository
import com.recipe2platekt.api.util.JwtTokenUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthFilter(
    private val appUserRepository: AppUserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
) : OncePerRequestFilter() {

    companion object {
        private val MAPPER = ObjectMapper()
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        authorizationHeader?.let { bearerHeaderToken ->
            println("hasAuthHeader")

            val splitHeader = bearerHeaderToken.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()

            println("Bearer" == splitHeader[0])
            if (splitHeader.size == 2 && "Bearer" == splitHeader[0]) {
                try {
                    SecurityContextHolder.getContext().authentication = validateToken(splitHeader[1])
                } catch (e: Exception) {
                    SecurityContextHolder.clearContext()
                    response.status = HttpServletResponse.SC_BAD_REQUEST
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    MAPPER.writeValue(response.outputStream, ErrorDto("Unauthenticated"))
                }
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun validateToken(token: String): Authentication {
        println("token got ${jwtTokenUtil.validateTokenWithSubject(token)}")

        val appUser: AppUser = appUserRepository.findByEmail(
            jwtTokenUtil.validateTokenWithSubject(token)
        ).orElseThrow { BadCredentialsException("Found Data") }
        return UsernamePasswordAuthenticationToken(
            appUser,
            null,
            listOf(appUser.role.roleName).map { SimpleGrantedAuthority(it) }
        )
    }
}