package com.recipe2platekt.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport
class Recipe2PlateKtApplication

fun main(args: Array<String>) {
    runApplication<Recipe2PlateKtApplication>(*args)
}
