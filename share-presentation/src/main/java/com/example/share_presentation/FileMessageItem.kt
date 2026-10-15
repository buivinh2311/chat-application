package com.example.share_presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_model.Message
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_utils.formatFileSize

@Composable
fun FileMessageItem(
    fileMessage: Message.File,
    reactionCounts: Map<String, Int>,
    isMine: Boolean,
    onDownloadClick: () -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onReactionClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
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
                        .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                        .border(
                            width = AppDimens.Border.Thin,
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(AppDimens.Radius.Sm)
                        )
                        .align(
                            alignment = if (isMine) Alignment.CenterEnd
                            else Alignment.CenterStart
                        )
                        .then(
                            if (isMine) {
                                Modifier.background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF0073DF),
                                            MaterialTheme.colorScheme.primary
                                        )
                                    )
                                )
                            } else {
                                Modifier.background(MaterialTheme.colorScheme.surface)
                            }
                        )
                        .combinedClickable(
                            onClick = onClick,
                            onLongClick = onLongClick
                        )
                        .padding(
                            vertical = AppDimens.Space.Md,
                            horizontal = AppDimens.Space.Sm
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(getFileImage(fileMessage.mimeType)),
                        contentDescription = "file image",
                        modifier = Modifier.size(AppDimens.ImageSize.Xs)
                    )
                    Spacer(modifier = Modifier.width(AppDimens.Space.Xs))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = fileMessage.fileName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xxs))

                        Text(
                            text = formatFileSize(fileMessage.fileSize),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(AppDimens.Space.Xxs))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                        }
                    }
//                    Spacer(modifier = Modifier.width(AppDimens.Space.Sm))
//
//                    Box(
//                        modifier = Modifier
//                            .clip(CircleShape)
//                            .size(AppDimens.Ripple.Lg)
//                            .background(Color.Gray.copy(alpha = 0.3f))
//                            .clickable {
//                                onDownloadClick()
//                            },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            painter = AppIcons.Down,
//                            contentDescription = "Download file",
//                            modifier = Modifier.size(AppDimens.Icon.Sm)
//                        )
//                    }
                }

                if(reactionCounts.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(
                                x = -AppDimens.Space.Sm,
                                y = AppDimens.Space.Lg
                            )
                            .clip(RoundedCornerShape(AppDimens.Radius.Xl))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(
                                width = AppDimens.Border.Thin,
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(AppDimens.Radius.Xl)
                            )
                            .clickable {
                                onReactionClick()
                            }
                            .padding(horizontal = AppDimens.Space.Xs + AppDimens.Space.Xxs),
                        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Xxs)
                    ) {
                        var value = 0
                        reactionCounts.forEach { (emoji, count) ->
                            value += count
                            Text(
                                text = emoji,
                                fontSize = 12.5.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        if(value != 0) {
                            Text(
                                text = value.toString(),
                                fontSize = 12.5.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }

    if (reactionCounts.isNotEmpty()) {
        Spacer(
            modifier = Modifier.height(AppDimens.Space.Lg)
        )
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