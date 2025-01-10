package com.polstat.digitalarchive.models

data class User(
    val name: String,
    val email: String,
    val password: String,
    val address: String?,
    val phoneNumber: String?
)
