package com.example.core_domain.repository

import android.net.Uri
import com.example.core_model.CloudinaryUploadResult
import java.io.File

interface StorageRepository {
    suspend fun upload(file: File): Result<CloudinaryUploadResult?>
}