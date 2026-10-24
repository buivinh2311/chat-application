package com.example.feature_conversation.presentation.conversationdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_resource.R
import coil.compose.AsyncImage
import com.example.core_model.Message
import com.example.core_model.User
import com.example.core_resource.AppDimens
import com.example.core_utils.getReactionCount
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun ImageMessageItem(
    sender: User?,
    imageMessage: Message.Image,
    isMine: Boolean,
    isHighlighted: Boolean,
    firstInGroup: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onReactionClick: () -> Unit,
    onDismiss: (() -> Unit)? = null
) {
    val showAvatar = firstInGroup && !isMine
    Row(
        modifier = Modifier
            .padding(horizontal = AppDimens.Space.Md)
            .then(
                if (firstInGroup) {
                    Modifier.padding(
                        top = AppDimens.Space.Sm,
                        bottom = AppDimens.Space.Xxs
                    )
                } else {
                    Modifier.padding(
                        start = AppDimens.Space.Xl + AppDimens.Space.Sm,
                        top = AppDimens.Space.Xxs,
                        bottom = AppDimens.Space.Xxs
                    )
                }
            ),
        horizontalArrangement = Arrangement.spacedBy(
            AppDimens.Space.Xs + AppDimens.Space.Xxs
        )
    ) {
        if (showAvatar) {
            AsyncImage(
                model = sender?.avatarUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.account),
                error = painterResource(R.drawable.account),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(AppDimens.ImageSize.Xxs)
                    .clip(CircleShape)
                    .border(
                        width = AppDimens.Border.Thin,
                        color = MaterialTheme.colorScheme
                            .outlineVariant.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
                    .clickable {

                    }
            )
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val maxImageWidth = maxWidth * 0.7f
            val maxImageHeight = 400.dp

            val originalWidth = imageMessage.width.toFloat()
            val originalHeight = imageMessage.height.toFloat()

            val widthRatio = maxImageWidth.value / originalWidth
            val heightRatio = maxImageHeight.value / originalHeight

            val scale = minOf(
                1f,
                widthRatio,
                heightRatio
            )

            val imageWidth = (originalWidth * scale).dp
            val imageHeight = (originalHeight * scale).dp

            if (isMine) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .fillMaxWidth(0.25f)
                        .align(Alignment.CenterStart)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) {
                            onDismiss?.invoke()
                        }
                )
            }

            if (!isMine) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .fillMaxWidth(if (showAvatar) 0.15f else 0.25f)
                        .align(Alignment.CenterEnd)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) {
                            onDismiss?.invoke()
                        }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(
                        if (showAvatar) 0.85f
                        else 0.75f
                    )
                    .align(
                        alignment = if (isMine) Alignment.CenterEnd
                        else Alignment.CenterStart
                    )
            ) {
                if (isMine) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onDismiss?.let {
                                    onDismiss()
                                }
                            }
                    )
                }

                Box {
                    AsyncImage(
                        model = imageMessage.fileUrl,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.ic_image),
                        error = painterResource(R.drawable.ic_image),
                        modifier = Modifier
                            .size(
                                width = imageWidth,
                                height = imageHeight
                            )
                            .clip(
                                RoundedCornerShape(
                                    AppDimens.Radius.Sm + AppDimens.Radius.Xxs
                                )
                            )
                            .border(
                                width = if (isHighlighted) {
                                    AppDimens.Border.Thick - AppDimens.Border.Normal
                                } else {
                                    AppDimens.Border.Thin
                                },
                                color = if (isHighlighted) {
                                    Color(0xFFE9A23B)
                                } else {
                                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)
                                },
                                shape = RoundedCornerShape(
                                    AppDimens.Radius.Sm + AppDimens.Radius.Xxs
                                )
                            )
                            .combinedClickable(
                                onClick = onClick,
                                onLongClick = onLongClick
                            )
                            .align(
                                alignment = if (isMine) Alignment.CenterEnd
                                else Alignment.CenterStart
                            ),
                        contentScale = ContentScale.Crop
                    )

                    if (imageMessage.reactions.isNotEmpty()) {
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
                            imageMessage.getReactionCount().forEach { (emoji, count) ->
                                value += count
                                Text(
                                    text = emoji,
                                    fontSize = 12.5.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            if (value != 0) {
                                Text(
                                    text = value.toString(),
                                    fontSize = 12.5.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }

                if (!isMine) {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onDismiss?.let {
                                    onDismiss()
                                }
                            }
                    )
                }
            }
        }
    }

    if (imageMessage.reactions.isNotEmpty()) {
        Spacer(
            modifier = Modifier.height(AppDimens.Space.Lg)
        )
    }
}