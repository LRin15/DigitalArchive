package com.polstat.digitalarchive.repository

import com.polstat.digitalarchive.di.NetworkModule
import com.polstat.digitalarchive.models.Archive
import com.polstat.digitalarchive.models.AuthRequest
import com.polstat.digitalarchive.models.User
import com.polstat.digitalarchive.utils.SessionManager

class Repository {
    private val apiService = NetworkModule.apiService

    suspend fun login(email: String, password: String) {
        val response = apiService.login(AuthRequest(email, password))
        SessionManager.saveToken(response.accessToken)
    }

    suspend fun register(user: User) = apiService.register(user)

    suspend fun getAllArchives() = apiService.getAllArchives()

    suspend fun getArchiveById(id: Long) = apiService.getArchiveById(id)

    suspend fun searchArchives(keyword: String) = apiService.searchArchives(keyword)

    suspend fun createArchive(archive: Archive) = apiService.createArchive(archive)

    suspend fun deleteArchive(id: Long) = apiService.deleteArchive(id)

    suspend fun updateProfile(userId: Long, user: User) = apiService.updateProfile(userId, user)

    suspend fun changePassword(userId: Long, oldPassword: String, newPassword: String) =
        apiService.changePassword(userId, oldPassword, newPassword)
}