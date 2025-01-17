package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polstat.digitalarchive.databinding.ActivityArchiveDetailBinding
import com.polstat.digitalarchive.viewmodels.MainViewModel
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.polstat.digitalarchive.models.Archive

class ArchiveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArchiveDetailBinding
    private val viewModel: MainViewModel by viewModels()
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchiveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val archiveId = intent.getLongExtra(EXTRA_ARCHIVE_ID, -1)
        if (archiveId != -1L) {
            setupObservers()
            setupClickListeners(archiveId)
            viewModel.loadArchiveById(archiveId)
        } else {
            Toast.makeText(this, "Invalid archive ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupClickListeners(archiveId: Long) {
        binding.btnEdit.setOnClickListener {
            toggleEditMode(true)
        }

        binding.btnDelete.setOnClickListener {
            showDeleteConfirmationDialog(archiveId)
        }

        binding.btnSave.setOnClickListener {
            saveChanges(archiveId)
        }

        binding.btnCancel.setOnClickListener {
            toggleEditMode(false)
            viewModel.loadArchiveById(archiveId)
        }
    }

    private fun setupObservers() {
        viewModel.selectedArchive.observe(this) { archive ->
            archive?.let {
                updateUI(it)
            }
        }

        viewModel.updateArchiveSuccess.observe(this) { success ->
            if (success) {
                StisToast.showSuccess(this, "Archive update successful!")
                toggleEditMode(false)
                // Refresh the archive data after successful update
                viewModel.selectedArchive.value?.id?.let {
                    viewModel.loadArchiveById(it)
                }
            }
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(archive: Archive) {
        binding.apply {
            // Update TextViews
            tvTitle.text = archive.title
            tvDescription.text = "Description: ${archive.description ?: "-"}"
            tvType.text = "Type: ${archive.archiveType}"
            tvFileUrl.text = "File URL: ${archive.fileUrl}"
            tvCreatedAt.text = "Created At: ${archive.createdAt ?: "-"}"

            // Update EditTexts
            etTitle.setText(archive.title)
            etDescription.setText(archive.description)
            etType.setText(archive.archiveType)
            etFileUrl.setText(archive.fileUrl)
        }
    }

    private fun toggleEditMode(enabled: Boolean) {
        isEditMode = enabled
        binding.apply {
            tvTitle.visibility = if (enabled) View.GONE else View.VISIBLE
            tvDescription.visibility = if (enabled) View.GONE else View.VISIBLE
            tvType.visibility = if (enabled) View.GONE else View.VISIBLE
            tvFileUrl.visibility = if (enabled) View.GONE else View.VISIBLE

            etTitle.visibility = if (enabled) View.VISIBLE else View.GONE
            etDescription.visibility = if (enabled) View.VISIBLE else View.GONE
            etType.visibility = if (enabled) View.VISIBLE else View.GONE
            etFileUrl.visibility = if (enabled) View.VISIBLE else View.GONE

            btnEdit.visibility = if (enabled) View.GONE else View.VISIBLE
            btnDelete.visibility = if (enabled) View.GONE else View.VISIBLE
            layoutEditButtons.visibility = if (enabled) View.VISIBLE else View.GONE
        }
    }

    private fun saveChanges(archiveId: Long) {
        val updatedArchive = Archive(
            id = archiveId,
            title = binding.etTitle.text.toString(),
            description = binding.etDescription.text.toString(),
            archiveType = binding.etType.text.toString(),
            fileUrl = binding.etFileUrl.text.toString(),
            createdAt = viewModel.selectedArchive.value?.createdAt
        )
        viewModel.updateArchive(archiveId, updatedArchive)
    }

    private fun showDeleteConfirmationDialog(archiveId: Long) {
        AlertDialog.Builder(this)
            .setTitle("Delete Archive")
            .setMessage("Are you sure you want to delete this archive?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteArchive(archiveId)
                StisToast.showSuccess(this, "Archive delete successful!")
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    companion object {
        private const val EXTRA_ARCHIVE_ID = "extra_archive_id"

        fun createIntent(context: Context, archiveId: Long) =
            Intent(context, ArchiveDetailActivity::class.java).apply {
                putExtra(EXTRA_ARCHIVE_ID, archiveId)
            }
    }
}