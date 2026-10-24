package com.example.feature_conversation.presentation.conversationdetail.component

import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.core_model.Message
import com.example.core_model.User
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_resource.R
import com.example.feature_conversation.domain.model.ConversationDetailAction
import com.example.feature_conversation.domain.model.MessageAction
import com.example.feature_conversation.domain.model.MessageActionItem

@Composable
fun MessagePopup(
    message: Message,
    pinnedMessageCount: Int,
    sender: User?,
    isMine: Boolean,
    onDismiss: () -> Unit,
    onAction: (ConversationDetailAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.8f))
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
                onDismiss()
            }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) {}
        ) {
            SelectedMessage(
                sender = sender,
                message = message,
                isMine = isMine,
                onDismiss = onDismiss
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Md))

            ReactionBar(
                onReactionClick = { emoji ->
                    onAction(
                        ConversationDetailAction.ReactToMessage(
                            messageId = message.id,
                            emoji = emoji
                        )
                    )
                    onDismiss()
                }
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Md))

            MessageActionBar(
                message = message,
                pinnedMessageCount = pinnedMessageCount,
                onAction = onAction,
                senderName = sender?.fullName ?: stringResource(R.string.text_unknown_user),
                isMine = isMine,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
private fun SelectedMessage(
    sender: User?,
    message: Message,
    isMine: Boolean,
    onDismiss: () -> Unit
) {
    when (message) {
        is Message.Text -> {
            TextMessageItem(
                sender = sender,
                textMessage = message,
                isMine = isMine,
                isHighlighted = false,
                firstInGroup = true,
                onLongClick = {},
                onReactionClick = {},
                onDismiss = onDismiss
            )
        }

        is Message.Image -> {
            ImageMessageItem(
                sender = sender,
                imageMessage = message,
                isMine = isMine,
                isHighlighted = false,
                firstInGroup = true,
                onClick = {},
                onLongClick = {},
                onReactionClick = {},
                onDismiss = onDismiss
            )
        }

        is Message.Video -> {
            VideoMessageItem(
                sender = sender,
                videoMessage = message,
                isMine = isMine,
                isHighlighted = false,
                firstInGroup = true,
                onClick = {},
                onLongClick = {},
                onReactionClick = {},
                onDismiss = onDismiss
            )
        }

        is Message.File -> {
            FileMessageItem(
                sender = sender,
                fileMessage = message,
                isMine = isMine,
                isHighlighted = false,
                firstInGroup = true,
                onClick = {},
                onLongClick = {},
                onReactionClick = {},
                onDismiss = onDismiss
            )
        }

        else -> {}
    }
}

@Composable
fun ReactionBar(
    modifier: Modifier = Modifier,
    onReactionClick: (String) -> Unit
) {
    val emojis = listOf("❤️", "👍", "😂", "😮", "😢", "😡")
    Row(
        modifier = modifier
            .padding(horizontal = AppDimens.Space.Md)
            .clip(RoundedCornerShape(AppDimens.Radius.Lg))
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = AppDimens.Space.Sm,
                vertical = AppDimens.Space.Xs
            ),
        horizontalArrangement = Arrangement.spacedBy(AppDimens.Space.Sm)
    ) {
        emojis.forEach { emoji ->
            Text(
                text = emoji,
                fontSize = 28.sp,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onReactionClick(emoji)
                    }
                    .padding(AppDimens.Space.Xs),
                textAlign = TextAlign.Center
            )
        }
    }
}
