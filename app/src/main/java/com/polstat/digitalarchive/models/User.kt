package com.polstat.digitalarchive.models

data class User(
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String,
    val address: String?,
    val phoneNumber: String?
)
