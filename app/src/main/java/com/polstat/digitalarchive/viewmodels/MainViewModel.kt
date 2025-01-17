package com.polstat.digitalarchive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polstat.digitalarchive.models.Archive
import com.polstat.digitalarchive.models.User
import com.polstat.digitalarchive.repository.Repository
import com.polstat.digitalarchive.utils.SessionManager
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = Repository()

    private val _archives = MutableLiveData<List<Archive>>()
    val archives: LiveData<List<Archive>> = _archives

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _selectedArchive = MutableLiveData<Archive>()
    val selectedArchive: LiveData<Archive> = _selectedArchive

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> = _registerSuccess

    private val _updateProfileSuccess = MutableLiveData<Boolean>()
    val updateProfileSuccess: LiveData<Boolean> = _updateProfileSuccess

    private val _changePasswordSuccess = MutableLiveData<Boolean>()
    val changePasswordSuccess: LiveData<Boolean> = _changePasswordSuccess

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> = _currentUser

    private val _updateArchiveSuccess = MutableLiveData<Boolean>()
    val updateArchiveSuccess: LiveData<Boolean> = _updateArchiveSuccess

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginResponse = repository.login(email, password)
                // Setelah login berhasil, ambil data user
                val user = repository.getCurrentUser(email)  // Tambahkan fungsi ini
                SessionManager.saveCurrentUser(user)
                _currentUser.value = user
                _loginSuccess.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _loginSuccess.value = false
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            try {
                repository.register(user)
                _registerSuccess.value = true  // Set true jika berhasil
            } catch (e: Exception) {
                _error.value = e.message
                _registerSuccess.value = false  // Set false jika gagal
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

    fun updateArchive(id: Long, archive: Archive) {
        viewModelScope.launch {
            try {
                repository.updateArchive(id, archive)
                _updateArchiveSuccess.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _updateArchiveSuccess.value = false
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

    fun getCurrentUser(email: String) {
        viewModelScope.launch {
            try {
                val user = repository.getCurrentUser(email)
                _currentUser.value = user
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateProfile(userId: Long, user: User) {
        viewModelScope.launch {
            try {
                repository.updateProfile(userId, user)
                // After successful update, get fresh user data
                getCurrentUser(user.email)
                _updateProfileSuccess.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _updateProfileSuccess.value = false
            }
        }
    }

    fun changePassword(userId: Long, oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                repository.changePassword(userId, oldPassword, newPassword)
                _changePasswordSuccess.value = true
            } catch (e: Exception) {
                _error.value = e.message
                _changePasswordSuccess.value = false
            }
        }
    }

    fun loadArchiveById(id: Long) {
        viewModelScope.launch {
            try {
                val archive = repository.getArchiveById(id)
                _selectedArchive.value = archive
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}