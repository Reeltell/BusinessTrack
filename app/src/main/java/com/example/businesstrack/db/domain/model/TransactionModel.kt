package com.example.businesstrack.db.domain.model

data class Transaction(
    val id: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val note: String = "",
    val type: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
