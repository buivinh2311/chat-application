package com.example.core_model.composite

data class MediaPreview (
    val fileUrl: String,
    val fileName: String,
    val mimeType: String,
    val sentAt: String,
    val avatarUrl: String?,
    val displayName: String
)