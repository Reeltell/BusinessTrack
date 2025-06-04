package com.example.businesstrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.businesstrack.ui.screen.AddTransaction.AddTransactionScreen
import com.example.businesstrack.ui.screen.Auth.AuthenticationScreen
import com.example.businesstrack.ui.screen.Home.HomeScreen
import com.example.businesstrack.ui.screen.Registr.RegistrationScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun BusinessNavHost() {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val startDestination = if (auth.currentUser != null) {
        Screen.Home.route
    } else {
        Screen.Registration.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Registration.route) {
            RegistrationScreen(
                onRegistrationSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Registration.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Authentication.route) {
                        popUpTo(Screen.Registration.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Authentication.route) {
            AuthenticationScreen(
                navigateToBack = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Authentication.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Registration.route) {
                        popUpTo(Screen.Authentication.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onAddTransactionClick = { navController.navigate(Screen.AddTransaction.route) }
            )
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                onTransactionAdded = { navController.popBackStack() }
            )
        }

    }
}
