package com.decagonhq.clads.data.domain

data class GenericResponseClass<T>(
    val message: String,
    val payload: T,
    val status: Int
)
