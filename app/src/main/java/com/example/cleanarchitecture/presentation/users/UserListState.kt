package com.example.cleanarchitecture.presentation.users

import com.example.cleanarchitecture.domain.model.User

data class UserListState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val query: String = "",
    val error: String? = null,
)
