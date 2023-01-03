package com.recipe2platekt.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer
import com.recipe2platekt.api.http.response.ErrorDto

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Any?>? {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.fieldErrors.forEach(Consumer { value: FieldError ->
            errors[value.field] = value.defaultMessage
        })
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(ex: BindException): ResponseEntity<*>? {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.fieldErrors.forEach(Consumer { value: FieldError ->
            errors[value.field] = value.defaultMessage
        })
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<Map<String, String?>>(errors)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleRecordNotFoundException(ex: NotFoundException): ResponseEntity<ErrorDto?>? {
        val errorDto = ErrorDto(ex.message!!)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body<ErrorDto?>(errorDto)
    }


    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ErrorDto?>? {
        val errorDto = ErrorDto(ex.message!!)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body<ErrorDto?>(errorDto)
    }


    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<ErrorDto?>? {
        val errorDto = ErrorDto(ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body<ErrorDto?>(errorDto)
    }

    @ExceptionHandler(AlreadyExistsException::class)
    fun handleAlreadyExistsException(ex: AlreadyExistsException): ResponseEntity<ErrorDto?>? {
        val errorDto = ErrorDto(ex.message!!)
        return ResponseEntity.status(HttpStatus.CONFLICT).body<ErrorDto?>(errorDto)
    }
}