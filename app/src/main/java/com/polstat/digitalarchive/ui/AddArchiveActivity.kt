package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polstat.digitalarchive.databinding.ActivityAddArchiveBinding
import com.polstat.digitalarchive.models.Archive
import com.polstat.digitalarchive.viewmodels.MainViewModel

class AddArchiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddArchiveBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                val archive = Archive(
                    id = null,
                    title = binding.etTitle.text.toString().trim(),
                    description = binding.etDescription.text.toString().trim(),
                    archiveType = binding.etType.text.toString().trim(),
                    fileUrl = binding.etFileUrl.text.toString().trim(),
                    createdAt = null
                )

                viewModel.createArchive(archive)
                StisToast.showSuccess(this, "Archive add successful!")

                // Navigate back to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                StisToast.showInfo(this, "Please fill all required fields")
            }
        }
    }

    private fun validateInputs(): Boolean {
        return !(binding.etTitle.text.toString().trim().isEmpty() ||
                binding.etDescription.text.toString().trim().isEmpty() ||
                binding.etType.text.toString().trim().isEmpty() ||
                binding.etFileUrl.text.toString().trim().isEmpty())
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, AddArchiveActivity::class.java)
    }
}