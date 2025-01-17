package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polstat.digitalarchive.databinding.ActivityProfileBinding
import com.polstat.digitalarchive.models.User
import com.polstat.digitalarchive.utils.SessionManager
import com.polstat.digitalarchive.viewmodels.MainViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load current user data
        SessionManager.getCurrentUser()?.let { user ->
            binding.apply {
                etName.setText(user.name)
                etEmail.setText(user.email)
                etAddress.setText(user.address)
                etPhone.setText(user.phoneNumber)
            }
        } ?: run {
            // Jika tidak ada user tersimpan, kembali ke login
            startActivity(LoginActivity.createIntent(this))
            finish()
            return
        }

        setupObservers()
        setupClickListeners()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    private fun setupClickListeners() {
        binding.btnSaveProfile.setOnClickListener {
            if (!validateProfileInputs()) return@setOnClickListener

            val user = User(
                id = SessionManager.getCurrentUser()?.id,
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                password = "",  // We don't update password here
                address = binding.etAddress.text.toString(),
                phoneNumber = binding.etPhone.text.toString()
            )

            SessionManager.getCurrentUser()?.id?.let { userId ->
                viewModel.updateProfile(userId, user)
            }
        }

        binding.btnChangePassword.setOnClickListener {
            if (!validatePasswordInputs()) return@setOnClickListener

            val oldPassword = binding.etOldPassword.text.toString()
            val newPassword = binding.etNewPassword.text.toString()

            SessionManager.getCurrentUser()?.id?.let { userId ->
                viewModel.changePassword(userId, oldPassword, newPassword)
                StisToast.showSuccess(this, "Password updated successfully")
            }
        }
    }

    private fun validateProfileInputs(): Boolean {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val address = binding.etAddress.text.toString()
        val phone = binding.etPhone.text.toString()

        if (name.isBlank() || email.isBlank()) {
            StisToast.showInfo(this, "Name and email are required")
            return false
        }
        return true
    }

    private fun validatePasswordInputs(): Boolean {
        val oldPassword = binding.etOldPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()

        if (oldPassword.isBlank() || newPassword.isBlank()) {
            StisToast.showInfo(this, "Both passwords are required")
            return false
        }
        return true
    }

    private fun setupObservers() {
        viewModel.updateProfileSuccess.observe(this) { success ->
            if (success) {
                // Get the updated user data from the ViewModel
                viewModel.getCurrentUser(binding.etEmail.text.toString())
                StisToast.showSuccess(this, "Profile updated successfully")
            }
        }

        // Add observer for currentUser
        viewModel.currentUser.observe(this) { user ->
            user?.let {
                // Update SessionManager with the latest user data
                SessionManager.saveCurrentUser(it)
                // Update UI
                binding.apply {
                    etName.setText(it.name)
                    etEmail.setText(it.email)
                    etAddress.setText(it.address)
                    etPhone.setText(it.phoneNumber)
                }
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}