package com.example.feature_chat.domain.model

import java.io.File

data class FileInfo(
    val file: File,
    val mimeType: String
)