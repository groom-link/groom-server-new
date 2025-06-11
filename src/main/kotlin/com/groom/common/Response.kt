package com.groom.common


data class Response<T>(val success: Boolean, val body: T) {
    constructor(body: T) : this(body is ExceptionResponse, body)
}

@JvmRecord
data class ExceptionResponse(val status: Int, val message: String) {
    companion object {
        fun of(status: Int, e: Throwable): ExceptionResponse {
            return ExceptionResponse(status, e.localizedMessage)
        }
    }
}