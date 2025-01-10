package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.polstat.digitalarchive.databinding.ActivityArchiveDetailBinding
import com.polstat.digitalarchive.viewmodels.MainViewModel

class ArchiveDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArchiveDetailBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchiveDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val archiveId = intent.getLongExtra(EXTRA_ARCHIVE_ID, -1)
        if (archiveId != -1L) {
            // Implement archive detail view
        }
    }

    companion object {
        private const val EXTRA_ARCHIVE_ID = "extra_archive_id"
        fun createIntent(context: Context, archiveId: Long) =
            Intent(context, ArchiveDetailActivity::class.java).apply {
                putExtra(EXTRA_ARCHIVE_ID, archiveId)
            }
    }
}