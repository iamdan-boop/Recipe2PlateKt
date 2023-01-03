package com.recipe2platekt.api.security

import com.recipe2platekt.api.repositories.AppUserRepository
import com.recipe2platekt.api.security.filters.JwtAuthFilter
import com.recipe2platekt.api.util.JwtTokenUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebbSecurityConfig(
    private val userAuthenticationEntryPoint: UserAuthenticationEntryPoint,
    private val jwtTokenUtil: JwtTokenUtil,
    private val appUserRepository: AppUserRepository
) {

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/storage/**",
                "/webjars/**"
            )
        }
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint).and()
            .addFilterBefore(JwtAuthFilter(appUserRepository, jwtTokenUtil), BasicAuthenticationFilter::class.java)
            .csrf().disable()
            .cors().configurationSource(corsConfigurationSource()).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests { authorizeRequest ->
                authorizeRequest.requestMatchers(HttpMethod.POST, "/signIn", "/signUp").permitAll()
            }.authorizeHttpRequests { it.anyRequest().permitAll() }
        return httpSecurity.build()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val configurationSource = CorsConfiguration()
        configurationSource.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configurationSource.allowedOrigins = listOf("http://localhost:4200")
        configurationSource.allowedHeaders = listOf("*")
        configurationSource.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configurationSource)
        return source
    }
}