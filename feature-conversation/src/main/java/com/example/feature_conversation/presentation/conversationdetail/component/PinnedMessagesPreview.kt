package com.example.feature_conversation.presentation.conversationdetail.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_model.Message
import com.example.core_model.User
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.core_ui.component.AppIconButton
import kotlin.collections.first



@Composable
fun PinnedMessagesPreview(
    expanded: Boolean,
    users: List<User>,
    pinnedMessages: List<Message>,
    modifier: Modifier = Modifier,
    onExpandedChange: (Boolean) -> Unit,
    onMessageClick: (String) -> Unit
) {

    if (pinnedMessages.isEmpty()) return

    val latestPinnedMessage = pinnedMessages.first()

    val latestSender = users.find {
        it.id == latestPinnedMessage.senderId
    }

    Box(modifier = modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(
                    horizontal = AppDimens.Space.Xs,
                    vertical = AppDimens.Space.Xs
                )
                .clip(RoundedCornerShape(AppDimens.Radius.Sm))
                .border(
                    width = AppDimens.Border.Thin,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(AppDimens.Radius.Sm)
                ),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = if (expanded) AppDimens.Space.Sm else 0.dp
        ) {
            Column {
                AnimatedVisibility(
                    visible = !expanded,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppDimens.Space.Md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    onMessageClick(latestPinnedMessage.id)
                                    onExpandedChange(false)
                                }
                                .padding(
                                    vertical = AppDimens.Space.Sm,
                                    horizontal = AppDimens.Space.Md
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                        ) {
                            Icon(
                                painter = AppIcons.Chat,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(AppDimens.Icon.Sm)
                            )

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = messagePreviewText(latestPinnedMessage),
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(modifier = Modifier.height(AppDimens.Space.Xxs))

                                Text(
                                    text = stringResource(
                                        R.string.text_sent_by,
                                        latestSender?.fullName
                                            ?: stringResource(R.string.text_unknown_user)
                                    ),
                                    style = MaterialTheme.typography
                                        .bodySmall,
                                    color = MaterialTheme.colorScheme
                                        .onSurfaceVariant
                                )
                            }

                            MessageThumbnail(
                                message = latestPinnedMessage
                            )
                        }

                        VerticalDivider(
                            thickness = AppDimens.Divider.Normal,
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                            modifier = Modifier.height(
                                AppDimens.Space.Xl + AppDimens.Space.Md
                            )
                        )
                        Spacer(modifier = Modifier.width(AppDimens.Space.Md))

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(AppDimens.Radius.Xl))
                                .border(
                                    width = AppDimens.Border.Normal,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(AppDimens.Radius.Xl)
                                )
                                .clickable { onExpandedChange(true) }
                                .padding(
                                    horizontal = AppDimens.Space.Xs + AppDimens.Space.Xxs
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(pinnedMessages.size > 1) {
                                Text(
                                    text = stringResource(
                                        R.string.text_more_pin_message,
                                        pinnedMessages.size - 1
                                    ),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outlineVariant
                                )
                            }

                            Icon(
                                painter = AppIcons.Down,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outlineVariant,
                                modifier = Modifier.size(AppDimens.Icon.Lg),
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onExpandedChange(false) }
                                .padding(AppDimens.Space.Sm),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.title_pin_list),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.weight(1f)
                            )

                            Icon(
                                painter = AppIcons.Down,
                                contentDescription = stringResource(R.string.title_pin_list),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(AppDimens.Icon.Md)
                                    .graphicsLayer { rotationZ = 180f }
                            )
                            Spacer(modifier = Modifier.width(AppDimens.Space.Xs))
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            pinnedMessages.forEach { message ->
                                val sender = users.find { it.id == message.senderId }

                                HorizontalDivider(
                                    thickness = AppDimens.Divider.Normal,
                                    color = MaterialTheme.colorScheme
                                        .outlineVariant.copy(alpha = 0.6f)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = AppDimens.Space.Lg),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {
                                                onMessageClick(message.id)
                                                onExpandedChange(false)
                                            }
                                            .padding(
                                                vertical = AppDimens.Space.Sm,
                                                horizontal = AppDimens.Space.Md
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Md)
                                    ) {
                                        Icon(
                                            painter = AppIcons.Chat,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(AppDimens.Icon.Sm)
                                        )

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = messagePreviewText(message),
                                                style = MaterialTheme.typography.bodyMedium,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            Spacer(modifier = Modifier.height(AppDimens.Space.Xxs))

                                            Text(
                                                text = stringResource(
                                                    R.string.text_sent_by,
                                                    sender?.fullName
                                                        ?: stringResource(R.string.text_unknown_user)
                                                ),
                                                style = MaterialTheme.typography
                                                    .bodySmall,
                                                color = MaterialTheme.colorScheme
                                                    .onSurfaceVariant
                                            )
                                        }

                                        MessageThumbnail(message = message)
                                    }

                                    VerticalDivider(
                                        thickness = AppDimens.Divider.Normal,
                                        color = MaterialTheme.colorScheme
                                            .outlineVariant.copy(alpha = 0.6f),
                                        modifier = Modifier.height(
                                            AppDimens.Space.Xl + AppDimens.Space.Md
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(AppDimens.Space.Lg))

                                    AppIconButton(
                                        icon = AppIcons.NotDisturb,
                                        iconSize = AppDimens.Icon.Xs,
                                        color = MaterialTheme.colorScheme.outlineVariant,
                                        onClick = { }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun messagePreviewText(message: Message): String {
    return when (message) {
        is Message.Text -> message.text
        is Message.Image -> stringResource(R.string.text_image)
        is Message.Video -> stringResource(R.string.text_video)
        is Message.File -> stringResource(
            R.string.text_file,
            message.fileName
        )
        is Message.System -> ""
    }
}

@Composable
private fun MessageThumbnail(message: Message) {
    when (message) {
        is Message.Image -> {
            AsyncImage(
                model = message.fileUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(AppDimens.ImageSize.Xs)
                    .clip(RoundedCornerShape(AppDimens.Radius.Sm))
            )
        }

        is Message.Video -> {
            AsyncImage(
                model = message.thumbnailUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(AppDimens.ImageSize.Xs)
                    .clip(RoundedCornerShape(AppDimens.Radius.Sm))
            )
        }

        else -> Unit
    }
}

