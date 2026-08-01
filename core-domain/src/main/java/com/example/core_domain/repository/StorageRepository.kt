package com.example.core_domain.repository

import android.net.Uri
import java.io.File

interface StorageRepository {
    suspend fun uploadAvatar(
        uid: String,
        file: File
    ): String

    suspend fun deleteAvatar(uid: String): Result<Unit>
}