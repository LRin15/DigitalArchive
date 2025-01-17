package com.polstat.digitalarchive.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.polstat.digitalarchive.R
import com.polstat.digitalarchive.databinding.ActivityMainBinding
import com.polstat.digitalarchive.ui.adapters.ArchiveAdapter
import com.polstat.digitalarchive.utils.SessionManager
import com.polstat.digitalarchive.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var archiveAdapter: ArchiveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        setupRecyclerView()
        setupObservers()
        setupSearch()

        binding.fab.setOnClickListener {
            AddArchiveActivity.createIntent(this).also { startActivity(it) }
        }

        viewModel.loadArchives()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                startActivity(ProfileActivity.createIntent(this))
                true
            }
            R.id.menu_logout -> {
                SessionManager.logout()
                StisToast.showSuccess(this, "Logout successful!")
                startActivity(LoginActivity.createIntent(this))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    companion object {
        // Tambahkan method createIntent
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh current user data when returning to MainActivity
        SessionManager.getCurrentUser()?.let { user ->
            viewModel.getCurrentUser(user.email)
        }
        viewModel.loadArchives()
    }
}