package com.example.core_model

data class CloudinaryUploadResult(
    val url: String,
    val publicId: String,
    val resourceType: String,
    val format: String?,
    val bytes: Long,
    val width: Int?,
    val height: Int?,
    val duration: Long?
)