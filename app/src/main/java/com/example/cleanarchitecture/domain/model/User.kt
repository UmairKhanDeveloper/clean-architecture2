package com.example.cleanarchitecture.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val city: String,
    val company: String,
)
