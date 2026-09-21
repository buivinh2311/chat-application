package com.example.feature_chat.domain.usecase

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.*
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.feature_chat.domain.model.CapturedImage
import java.io.File

class TakePhotoUseCase {
    operator fun invoke(
        context: Context,
        imageCapture: ImageCapture,
        onSuccess: (CapturedImage) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val cameraDirectory = File(
            context.cacheDir,
            "camera"
        )

        if (!cameraDirectory.exists()) {
            cameraDirectory.mkdirs()
        }

        val photoFile = File(
            cameraDirectory,
            "${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : OnImageSavedCallback {
                override fun onImageSaved(
                    outputFileResults: OutputFileResults
                ) {
                    try {
                        val uri =
                            FileProvider.getUriForFile(
                                context,
                                "${context.packageName}.fileProvider",
                                photoFile
                            )
                        onSuccess(
                            CapturedImage(
                                file = photoFile,
                                uri = uri
                            )
                        )
                    } catch (exception: Exception) {

                        onError(exception)
                    }
                }

                override fun onError(
                    exception: ImageCaptureException
                ) {
                    onError(exception)
                }
            }
        )
    }
}