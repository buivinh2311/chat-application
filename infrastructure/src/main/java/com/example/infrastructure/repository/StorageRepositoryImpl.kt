package com.example.infrastructure.repository

import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.core_domain.repository.StorageRepository
import com.example.core_model.CloudinaryUploadResult
import com.example.core_network.api.CloudinaryApi
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
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
        val preset = "chat_media"
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

    override suspend fun upload(
        file: File,
        resourceType: String
    ): CloudinaryUploadResult? {
        return suspendCancellableCoroutine { continuation ->
            val requestId = MediaManager
                .get()
                .upload(file.absolutePath)
                .unsigned("chat_media")
                .option("resource_type", resourceType)
                .callback(object : UploadCallback {

                    override fun onStart(
                        requestId: String
                    ) = Unit

                    override fun onProgress(
                        requestId: String,
                        bytes: Long,
                        totalBytes: Long
                    ) = Unit

                    override fun onSuccess(
                        requestId: String,
                        resultData: Map<Any?, Any?>
                    ) {
                        val result = CloudinaryUploadResult(
                            url = resultData["secure_url"] as String,
                            publicId = resultData["public_id"] as String,
                            resourceType = resultData["resource_type"] as String,
                            format = resultData["format"] as? String,
                            bytes = (resultData["bytes"] as Number).toLong(),
                            width = (resultData["width"] as? Number)?.toInt(),
                            height = (resultData["height"] as? Number)?.toInt(),
                            duration = (resultData["duration"] as? Number)?.toLong()
                        )

                        continuation.resume(
                            result
                        ) { cause, _, _ ->

                        }
                    }

                    override fun onError(
                        requestId: String,
                        error: ErrorInfo
                    ) {
                        continuation.resume(
                            null
                        ) { cause, _, _ ->
                            ({
                            }).invoke()
                        }
                    }

                    override fun onReschedule(
                        requestId: String,
                        error: ErrorInfo
                    ) = Unit
                })
                .dispatch()

            continuation.invokeOnCancellation {
                MediaManager
                    .get()
                    .cancelRequest(requestId)
            }
        }
    }
}