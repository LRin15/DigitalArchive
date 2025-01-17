package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polstat.digitalarchive.databinding.ActivityLoginBinding
import com.polstat.digitalarchive.viewmodels.MainViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                StisToast.showInfo(this, "Please fill all required fields")
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            startActivity(RegisterActivity.createIntent(this))
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun setupObservers() {
        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                StisToast.showSuccess(this, "Login successful!")
                startActivity(MainActivity.createIntent(this))
                finish()
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                // Check if error is related to invalid credentials
                if (it.contains("400") || it.contains("unauthorized", ignoreCase = true)) {
                    StisToast.showError(this, "Email or password wrong")
                } else {
                    StisToast.showError(this, it)
                }
            }
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}