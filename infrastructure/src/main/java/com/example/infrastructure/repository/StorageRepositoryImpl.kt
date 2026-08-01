package com.example.infrastructure.repository

import android.net.Uri
import android.util.Log
import com.example.core_domain.repository.StorageRepository
import com.example.core_network.api.CloudinaryApi
import com.google.firebase.storage.FirebaseStorage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait
import java.io.File
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val cloudinaryApi: CloudinaryApi
) : StorageRepository {
    override suspend fun uploadAvatar(
        uid: String,
        file: File
    ): String {
        val requestFile =
            file.asRequestBody("image/*".toMediaType())
        val multipartBody =
            MultipartBody.Part.createFormData(
                name = "file",
                filename = file.name,
                body = requestFile
            )
        val preset = "chat_avatar"
            .toRequestBody("text/plain".toMediaType())
        val response =
            cloudinaryApi.uploadImage(
                file = multipartBody,
                uploadPreset = preset
            )
        return response.secureUrl
    }

    override suspend fun deleteAvatar(uid: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}