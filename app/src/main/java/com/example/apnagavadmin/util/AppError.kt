package com.example.apnagavadmin.util

sealed class AppError(val message: String) {
    data class NetworkError(val msg: String = "Network error occurred") : AppError(msg)
    data class FirestoreError(val msg: String) : AppError(msg)
    data class AuthError(val msg: String = "Authentication failed") : AppError(msg)
    data class UnknownError(val msg: String = "An unknown error occurred") : AppError(msg)
}
