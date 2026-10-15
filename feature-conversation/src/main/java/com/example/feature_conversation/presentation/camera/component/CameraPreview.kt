package com.example.feature_conversation.presentation.camera.component

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.feature_conversation.domain.model.CapturedImage
import java.io.File

@Composable
fun CameraPreview(
    onImageCaptured: (CapturedImage) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(
                ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
            )
            .build()
    }

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    var isCapturing by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(lifecycleOwner) {
        try {
            val cameraProvider =
                cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.surfaceProvider =
                        previewView.surfaceProvider
                }

            val cameraSelector =
                CameraSelector.DEFAULT_BACK_CAMERA

            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        } catch (exception: Exception) {
            Log.e(
                "CameraX",
                "Camera initialization failed",
                exception
            )
        }
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            try {
                cameraProviderFuture.get().unbindAll()
            } catch (exception: Exception) {
                Log.e(
                    "CameraX",
                    "Failed to release camera",
                    exception
                )
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = {
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )
        CameraTopBar(
            onBack = onBack
        )
        CameraBottomBar(
            enabled = !isCapturing,
            onCapture = {
                isCapturing = true
                takePhoto(
                    context = context,
                    imageCapture = imageCapture,
                    onSuccess = { image ->
                        isCapturing = false
                        onImageCaptured(image)
                    },
                    onError = {
                        isCapturing = false
                    }
                )
            }
        )
    }
}

private fun takePhoto(
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

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(
                outputFileResults:
                ImageCapture.OutputFileResults
            ) {
                try {
                    val uri =
                        FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",
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