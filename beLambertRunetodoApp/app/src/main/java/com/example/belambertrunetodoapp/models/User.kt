package com.example.belambertrunetodoapp.models

data class User(
    val id: Int,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val password: String, // Consider security implications for storing passwords
    val isActive: Boolean
) {
    // Empty constructor
    constructor() : this(0, "", "", "", "", false)
}
