package com.example.cleanarchitecture.presentation.navigation

sealed class Screen(val route: String) {
    data object UserList : Screen("users")

    data object UserDetail : Screen("users/{$ARG_USER_ID}") {
        fun createRoute(userId: Int): String = "users/$userId"
    }

    companion object {
        const val ARG_USER_ID = "userId"
    }
}
