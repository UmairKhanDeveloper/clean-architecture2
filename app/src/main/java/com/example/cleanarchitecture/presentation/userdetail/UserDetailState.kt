package com.example.cleanarchitecture.presentation.userdetail

import com.example.cleanarchitecture.domain.model.User

data class UserDetailState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
)
