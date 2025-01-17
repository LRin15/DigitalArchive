package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polstat.digitalarchive.databinding.ActivityRegisterBinding
import com.polstat.digitalarchive.models.User
import com.polstat.digitalarchive.viewmodels.MainViewModel
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val address = binding.etAddress.text.toString()
            val phone = binding.etPhone.text.toString()

            // Validate fields
            if (name.isBlank() || email.isBlank() || password.isBlank() ||
                address.isBlank() || phone.isBlank()) {
                StisToast.showInfo(this, "Please fill all required fields")
                return@setOnClickListener
            }

            val user = User(
                name = name,
                email = email,
                password = password,
                address = address,
                phoneNumber = phone
            )
            viewModel.register(user)
        }
    }

    private fun setupObservers() {
        viewModel.registerSuccess.observe(this) { success ->
            if (success) {
                StisToast.showSuccess(this, "Registration successful!")
                // Navigate back to login screen
                startActivity(LoginActivity.createIntent(this))
                finish()
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                StisToast.showError(this, it)
            }
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, RegisterActivity::class.java)
    }
}