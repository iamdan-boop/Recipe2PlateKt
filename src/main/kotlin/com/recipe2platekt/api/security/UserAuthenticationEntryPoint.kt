package com.recipe2platekt.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.recipe2platekt.api.http.response.ErrorDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class UserAuthenticationEntryPoint : AuthenticationEntryPoint {

    companion object {
        private val MAPPER = ObjectMapper()
    }

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        print(authException!!.message)

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        MAPPER.writeValue(response.outputStream, ErrorDto("Unauthorized Path"))
    }
}