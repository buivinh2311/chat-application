package com.example.feature_conversation.presentation.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.feature_conversation.domain.model.CapturedImage
import com.example.feature_conversation.presentation.camera.component.CameraImagePreview
import com.example.feature_conversation.presentation.camera.component.CameraPermission
import com.example.feature_conversation.presentation.camera.component.CameraPreview

@Composable
fun CameraScreen(
    onSend: (Uri) -> Unit,
    onBackClick: () -> Unit
) {
    var capturedImage by remember {
        mutableStateOf<CapturedImage?>(null)
    }

    val context = LocalContext.current

    capturedImage?.let { image ->
        CameraImagePreview(
            imageUri = image.uri,
            onRetake = {
                image.file.delete()
                capturedImage = null
            },
            onSend = {
                onSend(image.uri)
            }
        )
    }

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasPermission = granted
        }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    if (hasPermission) {
        CameraPreview(
            onImageCaptured = { image ->
                capturedImage = image
            },
            onBackClick = onBackClick
        )
    } else {
        CameraPermission(
            onRequestPermission = {
                permissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            },
            onBackClick = onBackClick
        )
    }
}