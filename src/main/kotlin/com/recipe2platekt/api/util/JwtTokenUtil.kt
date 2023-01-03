package com.recipe2platekt.api.util

import com.recipe2platekt.api.http.request.recipe.CreateRecipeRequest
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@Component
class JwtTokenUtil {

    @Value("\${security.jwt.token.secret-key}")
    private lateinit var secretKey: String

    fun generateToken(email: String): String {
        val claims = Jwts.claims().setSubject(email)
        val now = Instant.now()
        val hashSecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(1, ChronoUnit.HOURS)))
            .signWith(hashSecretKey, SignatureAlgorithm.HS256)
            .compact()
    }


    fun validateTokenWithSubject(authToken: String) : String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey.toByteArray())
            .build()
            .parseClaimsJws(authToken)
            .body
            .subject
    }
}