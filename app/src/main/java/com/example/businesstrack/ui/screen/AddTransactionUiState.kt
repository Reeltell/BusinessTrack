package com.example.businesstrack.ui.screen

data class AddTransactionUiState(
    val category: String = "",
    val amount: String = "",
    val note: String = "",
    val type: String = "expense",
    val isValid: Boolean = false
)
