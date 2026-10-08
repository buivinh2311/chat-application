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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_model.Message
import com.example.core_resource.AppDimens
import com.example.core_resource.AppIcons
import com.example.core_utils.getReactionCount
import com.example.feature_conversation.domain.model.MessageAction
import com.example.feature_conversation.domain.model.MessageActionItem
import com.example.share_presentation.TextMessageItem

@Composable
fun MessagePopup(
    message: Message,
    isMine: Boolean,
    onDismiss: () -> Unit,
    onReactToMessage: (String, String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black.copy(alpha = 0.2f)
            )
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
                message = message,
                isMine = isMine
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Md))

            ReactionBar(
                onReactionClick = { emoji ->
                    onReactToMessage(message.id, emoji)
                    onDismiss()
                }
            )
            Spacer(modifier = Modifier.height(AppDimens.Space.Md))

            ActionBar()
        }
    }
}

@Composable
private fun SelectedMessage(
    message: Message,
    isMine: Boolean
) {
    when (message) {
        is Message.Text -> {
            TextMessageItem(
                reactionCounts = message.getReactionCount(),
                text = message.text,
                isMine = isMine,
                onLongClick = {},
                onReactionClick = {}
            )
        }

        else  -> {}
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

@Composable
fun ActionBar(
    modifier: Modifier = Modifier
) {
    val actions = listOf(
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = "chia se",
            action = MessageAction.REPLY
        ),
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = "chia se",
            action = MessageAction.REPLY
        ),
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = "chia se",
            action = MessageAction.REPLY
        ),
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = "chia se",
            action = MessageAction.REPLY
        ),
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = "chia se",
            action = MessageAction.REPLY
        ),
        MessageActionItem(
            icon = AppIcons.Report,
            tint = MaterialTheme.colorScheme.primary,
            title = "chia se",
            action = MessageAction.REPLY
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
                            MessageAction.REPLY -> {}
                            else -> {}
                        }
                    }
                    .padding(AppDimens.Space.Md),
                verticalArrangement = Arrangement.spacedBy(AppDimens.Space.Xs),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = item.icon,
                    contentDescription = null,
                    tint = item.tint,
                    modifier = Modifier.size(AppDimens.Icon.Sm)
                )

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
