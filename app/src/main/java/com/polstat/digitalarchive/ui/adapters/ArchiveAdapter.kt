package com.polstat.digitalarchive.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polstat.digitalarchive.databinding.ItemArchiveBinding
import com.polstat.digitalarchive.models.Archive

class ArchiveAdapter(
    private val onItemClick: (Archive) -> Unit
) : ListAdapter<Archive, ArchiveAdapter.ArchiveViewHolder>(ArchiveDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        return ArchiveViewHolder(
            ItemArchiveBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArchiveViewHolder(
        private val binding: ItemArchiveBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(archive: Archive) {
            binding.apply {
                tvTitle.text = archive.title
                tvDescription.text = archive.description
                root.setOnClickListener {
                    // Pastikan archive.id tidak null sebelum memanggil callback
                    archive.id?.let { id -> onItemClick(archive) }
                }
            }
        }
    }
}

class ArchiveDiffCallback : DiffUtil.ItemCallback<Archive>() {
    override fun areItemsTheSame(oldItem: Archive, newItem: Archive): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Archive, newItem: Archive): Boolean {
        return oldItem == newItem
    }
}