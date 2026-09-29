package com.example.cleanarchitecture.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanarchitecture.presentation.userdetail.UserDetailScreen
import com.example.cleanarchitecture.presentation.users.UserListScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.UserList.route) {
        composable(Screen.UserList.route) {
            UserListScreen(
                onUserClick = { userId ->
                    navController.navigate(Screen.UserDetail.createRoute(userId))
                },
            )
        }
        composable(
            route = Screen.UserDetail.route,
            arguments = listOf(navArgument(Screen.ARG_USER_ID) { type = NavType.IntType }),
        ) {
            UserDetailScreen(onBack = navController::popBackStack)
        }
    }
}
