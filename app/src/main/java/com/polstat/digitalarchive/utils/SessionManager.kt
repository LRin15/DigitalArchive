package com.polstat.digitalarchive.utils

import com.polstat.digitalarchive.models.User

object SessionManager {
    private var token: String? = null
    private var currentUser: User? = null

    fun saveToken(token: String) {
        this.token = token
    }

    fun getToken(): String? = token

    fun clearToken() {
        token = null
    }

    fun saveCurrentUser(user: User) {
        currentUser = user
    }

    fun getCurrentUser(): User? = currentUser

    fun updateCurrentUser(user: User) {
        currentUser = currentUser?.copy(
            name = user.name,
            email = user.email,
            address = user.address,
            phoneNumber = user.phoneNumber
        )
    }

    fun clearCurrentUser() {
        currentUser = null
    }

    fun logout() {
        clearToken()
        clearCurrentUser()
    }
}