package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
            val archive = Archive(
                id = null,
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                archiveType = binding.etType.text.toString(),
                fileUrl = binding.etFileUrl.text.toString(),
                createdAt = null
            )
            viewModel.createArchive(archive)
            finish()
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, AddArchiveActivity::class.java)
    }
}