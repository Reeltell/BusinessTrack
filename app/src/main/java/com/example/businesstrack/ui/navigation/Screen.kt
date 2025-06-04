package com.example.businesstrack.ui.navigation

sealed class Screen(val route: String) {
    object Registration : Screen("registration")
    object Authentication : Screen("authentication")
    object Home : Screen("home")
    object AddTransaction : Screen("add_transaction")
    object EditTransaction : Screen("edit_transaction")
}
