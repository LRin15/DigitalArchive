package com.polstat.digitalarchive.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.polstat.digitalarchive.databinding.ActivityMainBinding
import com.polstat.digitalarchive.ui.adapters.ArchiveAdapter
import com.polstat.digitalarchive.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var archiveAdapter: ArchiveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupSearch()

        binding.fab.setOnClickListener {
            AddArchiveActivity.createIntent(this).also { startActivity(it) }
        }

        viewModel.loadArchives()
    }

    private fun setupRecyclerView() {
        archiveAdapter = ArchiveAdapter { archive ->
            ArchiveDetailActivity.createIntent(this, archive.id!!).also { startActivity(it) }
        }
        binding.rvArchives.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = archiveAdapter
        }
    }

    private fun setupObservers() {
        viewModel.archives.observe(this) { archives ->
            archiveAdapter.submitList(archives)
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchArchives(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
}