package com.spectro.tech.rickycatalog.util

data class BasicApiResponse <out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): BasicApiResponse<T> {
            return BasicApiResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): BasicApiResponse<T> {
            return BasicApiResponse(Status.ERROR, data, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR
}