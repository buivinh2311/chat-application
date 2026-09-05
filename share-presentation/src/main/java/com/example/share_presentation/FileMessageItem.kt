package com.example.share_presentation

import android.text.format.Formatter.formatFileSize
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_utils.formatFileSize
import java.util.Locale

@Composable
fun FileMessageItem(
    fileName: String,
    fileSize: Long,
    mimeType: String,
    isMine: Boolean,
    onDownloadClick: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(
                    alignment = if (isMine) Alignment.CenterEnd
                    else Alignment.CenterStart
                )
                .padding(
                    horizontal = AppDimens.Space.Md
                )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(AppDimens.Radius.Md))
                        .align(
                            alignment = if (isMine) Alignment.CenterEnd
                            else Alignment.CenterStart
                        )
                        .background(
                            if (isMine)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                        .clickable(onClick = onClick)
                        .padding(AppDimens.Space.Md)
                        .widthIn(max = 280.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(getFileImage(mimeType)),
                        contentDescription = "file image",
                        modifier = Modifier.size(AppDimens.ImageSize.Sm)
                    )
                    Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = fileName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xxs))

                        Text(
                            text = formatFileSize(fileSize),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xxs))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                        }
                    }
                    Spacer(modifier = Modifier.width(AppDimens.Space.Sm))

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(AppDimens.Ripple.Lg)
                            .background(Color.Gray.copy(alpha = 0.3f))
                            .clickable {
                                onDownloadClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = AppIcons.Down,
                            contentDescription = "Download file",
                            modifier = Modifier.size(AppDimens.Icon.Sm)
                        )
                    }
                }
            }
        }
    }
}

@DrawableRes
fun getFileImage(mimeType: String): Int {
    return when {
        mimeType == "application/pdf" -> R.drawable.pdf

        mimeType == "application/zip" ||
                mimeType == "application/x-rar-compressed" ||
                mimeType == "application/x-7z-compressed" ->
            R.drawable.zip

        mimeType == "text/plain" -> R.drawable.file

        mimeType == "application/msword" ||
                mimeType == "application/vnd.openxmlformats-officedocument.wordprocessingml.document" ->
            R.drawable.doc

        mimeType == "application/vnd.ms-excel" ||
                mimeType == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ->
            R.drawable.sheets

        mimeType == "application/vnd.ms-powerpoint" ||
                mimeType == "application/vnd.openxmlformats-officedocument.presentationml.presentation" ->
            R.drawable.ppt

        else -> R.drawable.file
    }
}