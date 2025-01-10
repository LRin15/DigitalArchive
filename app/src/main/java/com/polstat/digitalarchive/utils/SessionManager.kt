package com.polstat.digitalarchive.utils

object SessionManager {
    private var token: String? = null

    fun saveToken(token: String) {
        this.token = token
    }

    fun getToken(): String? = token

    fun clearToken() {
        token = null
    }
}