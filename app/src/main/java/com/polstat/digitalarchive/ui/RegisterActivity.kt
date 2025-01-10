package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polstat.digitalarchive.databinding.ActivityRegisterBinding
import com.polstat.digitalarchive.models.User
import com.polstat.digitalarchive.viewmodels.MainViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val user = User(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
                address = binding.etAddress.text.toString(),
                phoneNumber = binding.etPhone.text.toString()
            )
            viewModel.register(user)
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, RegisterActivity::class.java)
    }
}