package com.example.feature_conversation.presentation.conversationdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.core_model.Message
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.feature_conversation.domain.model.ConversationDetailAction
import com.example.feature_conversation.domain.model.MessageAction
import com.example.feature_conversation.domain.model.MessageActionItem

@Composable
fun MessageActionBar(
    modifier: Modifier = Modifier,
    message: Message,
    pinnedMessageCount: Int,
    senderName: String,
    isMine: Boolean,
    onDismiss: () -> Unit,
    onAction: (ConversationDetailAction) -> Unit
) {
    val isPinnedMessage = when(message) {
        is Message.Text -> message.pinnedByUserId != null
        is Message.Image -> message.pinnedByUserId != null
        is Message.Video -> message.pinnedByUserId != null
        is Message.File -> message.pinnedByUserId != null
        else -> false
    }
    val actions = listOfNotNull(
        MessageActionItem(
            icon = AppIcons.Reply,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            title = stringResource(R.string.label_reply),
            action = MessageAction.REPLY
        ),
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = stringResource(R.string.label_forward),
            action = MessageAction.FORWARD
        ),
        if (message is Message.Text) {
            MessageActionItem(
                icon = AppIcons.Report,
                tint = MaterialTheme.colorScheme.primary,
                title = stringResource(R.string.label_copy),
                action = MessageAction.COPY
            )
        } else {
            MessageActionItem(
                icon = AppIcons.Download,
                tint = MaterialTheme.colorScheme.primary,
                title = stringResource(R.string.label_download),
                action = MessageAction.DOWNLOAD
            )
        },
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = stringResource(R.string.label_recall),
            action = MessageAction.RECALL
        ).takeIf { isMine },
        if(isPinnedMessage) {
            MessageActionItem(
                icon = AppIcons.Unpin,
                tint = MaterialTheme.colorScheme.primary,
                title = stringResource(R.string.label_unpin),
                action = MessageAction.UNPIN
            )
        } else {
            MessageActionItem(
                icon = AppIcons.Pin,
                tint = MaterialTheme.colorScheme.primary,
                title = stringResource(R.string.label_pin),
                action = MessageAction.PIN
            )
        },
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = stringResource(R.string.label_detail),
            action = MessageAction.DETAIL
        ),
        MessageActionItem(
            icon = AppIcons.Delete,
            tint = MaterialTheme.colorScheme.primary,
            title = stringResource(R.string.label_delete),
            action = MessageAction.DELETE
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppDimens.Space.Md)
            .clip(RoundedCornerShape(AppDimens.Space.Lg))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(
                top = AppDimens.Space.Sm,
                bottom = AppDimens.Space.Md
            )
    ) {
        items(actions) { item ->
            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(AppDimens.Space.Sm))
                    .clickable {
                        when (item.action) {
                            MessageAction.REPLY -> {
                                onAction(
                                    ConversationDetailAction.Reply(message, senderName)
                                )
                            }

                            MessageAction.FORWARD -> {
                                onAction(
                                    ConversationDetailAction.Forward(message.id)
                                )
                            }

                            MessageAction.COPY -> {
                                if (message is Message.Text) {
                                    onAction(
                                        ConversationDetailAction.Copy(message.text)
                                    )
                                }
                            }

                            MessageAction.DOWNLOAD -> {
                                when (message) {
                                    is Message.Image -> {
                                        onAction(
                                            ConversationDetailAction
                                                .DownloadImage(message)
                                        )
                                    }

                                    is Message.Video -> {
                                        onAction(
                                            ConversationDetailAction
                                                .DownloadVideo(message)
                                        )
                                    }

                                    is Message.File -> {
                                        onAction(
                                            ConversationDetailAction
                                                .DownloadFile(message)
                                        )
                                    }

                                    else -> {}
                                }
                            }

                            MessageAction.RECALL -> {

                            }

                            MessageAction.PIN -> {
                                onAction(
                                    ConversationDetailAction.Pin(
                                        messageId = message.id,
                                        pinnedMessageCount = pinnedMessageCount
                                    )
                                )
                            }

                            MessageAction.UNPIN -> {
                                onAction(
                                    ConversationDetailAction.Unpin(message.id)
                                )
                            }

                            MessageAction.DETAIL -> {

                            }

                            MessageAction.DELETE -> {

                            }
                        }
                        onDismiss()
                    }
                    .padding(
                        vertical = AppDimens.Space.Md,
                        horizontal = AppDimens.Space.Sm
                    ),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xs),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = item.icon,
                    contentDescription = null,
                    tint = item.tint,
                    modifier = Modifier.size(AppDimens.Icon.Md)
                )

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}