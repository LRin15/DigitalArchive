package com.polstat.digitalarchive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polstat.digitalarchive.models.Archive
import com.polstat.digitalarchive.models.User
import com.polstat.digitalarchive.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = Repository()

    private val _archives = MutableLiveData<List<Archive>>()
    val archives: LiveData<List<Archive>> = _archives

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                repository.login(email, password)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            try {
                repository.register(user)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadArchives() {
        viewModelScope.launch {
            try {
                _archives.value = repository.getAllArchives()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun searchArchives(keyword: String) {
        viewModelScope.launch {
            try {
                _archives.value = repository.searchArchives(keyword)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun createArchive(archive: Archive) {
        viewModelScope.launch {
            try {
                repository.createArchive(archive)
                loadArchives()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteArchive(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteArchive(id)
                loadArchives()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateProfile(userId: Long, user: User) {
        viewModelScope.launch {
            try {
                repository.updateProfile(userId, user)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun changePassword(userId: Long, oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                repository.changePassword(userId, oldPassword, newPassword)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}