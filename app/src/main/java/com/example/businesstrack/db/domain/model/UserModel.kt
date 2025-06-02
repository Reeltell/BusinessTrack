package com.example.businesstrack.db.domain.model

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: Long = 0L
)
