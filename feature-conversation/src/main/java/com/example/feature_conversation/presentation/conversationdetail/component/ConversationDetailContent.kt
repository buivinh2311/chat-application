package com.example.feature_conversation.presentation.conversationdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.core_model.Message
import com.example.core_model.User
import com.example.core_model.composite.MediaPreview
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_utils.getReactionCount
import com.example.feature_conversation.domain.model.ConversationDetailAction
import com.example.feature_conversation.presentation.conversationdetail.ConversationDetailData
import com.example.share_presentation.FileMessageItem
import com.example.share_presentation.ImageMessageItem
import com.example.share_presentation.TextMessageItem
import com.example.share_presentation.VideoMessageItem
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun ConversationDetailContent(
    data: ConversationDetailData,
    currentUser: User,
    isConnected: Boolean,
    isSentRequest: Boolean,
    messages: List<Message>,
    pinnedMessage: List<Message>,
    innerPadding: PaddingValues,
    onAction: (ConversationDetailAction) -> Unit
) {
    var selectedMessage by remember {
        mutableStateOf<Message?>(null)
    }
    var reactionMessage by remember {
        mutableStateOf<Message?>(null)
    }

    val users = when (data) {
        is ConversationDetailData.Direct -> listOf(data.user, currentUser)
        is ConversationDetailData.Group -> data.users.values.toList()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                top = AppDimens.Space.Xxl,
                bottom = AppDimens.Space.Lg
            )
        ) {
            stickyHeader {

            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = when (data) {
                            is ConversationDetailData.Direct -> data.user.avatarUrl
                            is ConversationDetailData.Group -> data.group.avatarUrl
                        },
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.account),
                        error = painterResource(R.drawable.account),
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .aspectRatio(1f)
                            .clip(shape = CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                if (isConnected) {
                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
//                    AppButton(
//                        modifier = Modifier.fillMaxWidth(0.35f),
//                        label = if(!isSentRequest)
//                            stringResource(R.string.add_friend)
//                        else
//                            stringResource(R.string.recall),
//                        color = if(!isSentRequest)
//                            MaterialTheme.colorScheme.primary
//                        else
//                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
//                        onClick = {
//                            if(isSentRequest) {
//                                onSendFriendRequest(user.id)
//                            } else {
//                                onRecallFriendRequest(user.id)
//                            }
//                        }
//                    )
                    }
                }
                Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))
            }

            items(
                count = messages.size,
                key = { index -> messages[index].id }
            ) { index ->
                val message = messages[index]
                val prevMessage = if (index != 0) messages[index - 1] else null
                val isMine = message.senderId == currentUser.id
                val sender = users.find { it.id == message.senderId }
                when (message) {
                    is Message.Text -> {
                        TextMessageItem(
                            textMessage = message,
                            isMine = isMine,
                            onLongClick = {
                                selectedMessage = message
                            },
                            onReactionClick = {
                                reactionMessage = message
                            }
                        )
                    }

                    is Message.Image -> {
                        ImageMessageItem(
                            imageMessage = message,
                            isMine = isMine,
                            onClick = {
                                val mediaPreview = MediaPreview(
                                    messageId = message.id,
                                    currentUser = currentUser,
                                    sender = sender,
                                    users = users
                                )
                                onAction(
                                    ConversationDetailAction.OpenMedia(mediaPreview)
                                )
                            },
                            onLongClick = {
                                selectedMessage = message
                            },
                            onReactionClick = {
                                reactionMessage = message
                            }
                        )
                    }

                    is Message.Video -> {
                        VideoMessageItem(
                            videoMessage = message,
                            reactionCounts = message.getReactionCount(),
                            isMine = isMine,
                            onClick = {
                                val mediaPreview = MediaPreview(
                                    messageId = message.id,
                                    currentUser = currentUser,
                                    sender = sender,
                                    users = users
                                )
                                onAction(
                                    ConversationDetailAction.OpenMedia(mediaPreview)
                                )
                            },
                            onLongClick = {
                                selectedMessage = message
                            },
                            onReactionClick = {
                                reactionMessage = message
                            }
                        )
                    }

                    is Message.File -> {
                        FileMessageItem(
                            fileMessage = message,
                            reactionCounts = message.getReactionCount(),
                            isMine = true,
                            onDownloadClick = {
                                onAction(
                                    ConversationDetailAction.DownloadFile(message)
                                )
                            },
                            onClick = {
                                onAction(
                                    ConversationDetailAction.OpenFile(message)
                                )
                            },
                            onLongClick = {
                                selectedMessage = message
                            },
                            onReactionClick = {
                                reactionMessage = message
                            }
                        )
                    }

                    is Message.System -> {

                    }
                }
            }
        }

        selectedMessage?.let { message ->
            val sender = users.find { it.id == message.senderId }
            MessagePopup(
                message = message,
                senderName = sender?.fullName.orEmpty(),
                isMine = message.senderId == currentUser.id,
                onDismiss = {
                    selectedMessage = null
                },
                onAction = onAction
            )
        }

        reactionMessage?.let { message ->
            val reactionUsers = message.reactions.mapNotNull { (userId, emoji) ->
                users.find { it.id == userId }?.let { user ->
                    user to emoji
                }
            }
            ReactionBottomSheet(
                currentUserId = currentUser.id,
                reactionUsers = reactionUsers,
                onDismissRequest = {
                    reactionMessage = null
                },
                onRemoveReaction = {
                    onAction(
                        ConversationDetailAction.RemoveReaction(message.id)
                    )
                }
            )
        }
    }
}