package com.recipe2platekt.api.exceptions

import org.apache.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(INTERNAL_SERVER_ERROR)
class ThirdPartyException(message: String?) : RuntimeException()