package com.polstat.digitalarchive.models

data class Archive(
    val id: Long?,
    val title: String,
    val description: String?,
    val archiveType: String,
    val fileUrl: String,
    val createdAt: String?
)
