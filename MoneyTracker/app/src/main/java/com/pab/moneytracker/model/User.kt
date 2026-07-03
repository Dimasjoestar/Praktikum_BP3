package com.pab.moneytracker.model

data class User(
    val id: Long = 0,
    val email: String,
    val password: String = "",
    val firstName: String,
    val lastName: String,
    val authProvider: AuthProvider = AuthProvider.EMAIL
)

enum class AuthProvider {
    EMAIL,
    GOOGLE,
    FACEBOOK
}
