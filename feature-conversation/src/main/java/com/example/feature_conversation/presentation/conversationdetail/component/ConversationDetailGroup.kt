package com.example.feature_conversation.presentation.conversationdetail.component

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.core_model.Group
import com.example.core_model.Message
import com.example.core_model.User
import com.example.core_model.composite.MediaPreview
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_utils.toRelativeString
import com.example.share_presentation.FileMessageItem
import com.example.share_presentation.ImageMessageItem
import com.example.share_presentation.TextMessageItem
import com.example.share_presentation.VideoMessageItem

@Composable
fun ConversationDetailGroup(
    group: Group,
    currentUserId: String,
    members: Map<String, User>,
    messages: List<Message>,
    innerPadding: PaddingValues,
//    onSendFriendRequest: ,
//    onRecallFriendRequest = ,
    onImageClick: (MediaPreview) -> Unit,
    onVideoClick: (MediaPreview) -> Unit,
    onDownloadFile: (Message.File) -> Unit,
    onOpenFile: (Message.File) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentPadding = PaddingValues(
            vertical = AppDimens.Space.Lg
        )
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = group.avatarUrl,
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
            Spacer(modifier = Modifier.height(AppDimens.Space.Lg))

//            AppButton(
//                label = stringResource(R.string.send_group_request),
//                color = MaterialTheme.colorScheme.primary,
//                onClick = {
//
//                }
//            )
        }

        items(
            count = messages.size,
            key = { index -> messages[index].id }
        ) { index ->
            val message = messages[index]
            val prevMessage = if(index != 0) messages[index - 1] else null
            val isMine = message.senderId == currentUserId
            val sender = members[message.senderId]
            when (message) {
                is Message.Text -> {
                    prevMessage?.let {
                        if(prevMessage.senderId != message.senderId) {
                            Spacer(modifier = Modifier.height(AppDimens.Space.Md))
                        } else {
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                        }
                    }

                    TextMessageItem(
                        text = message.text,
                        isMine = isMine
                    )
                }

                is Message.Image -> {
                    ImageMessageItem(
                        fileUrl = message.fileUrl,
                        width = message.width,
                        height = message.height,
                        isMine = isMine,
                        onClick = {
                            val mediaPreview = MediaPreview(
                                fileUrl = message.fileUrl,
                                fileName = message.fileName,
                                mimeType = message.mimeType,
                                sentAt = message.createdAt.toRelativeString(),
                                avatarUrl = sender?.avatarUrl,
                                displayName = sender?.fullName.orEmpty()
                            )
                            onImageClick(mediaPreview)
                        }
                    )
                }

                is Message.Video -> {
                    VideoMessageItem(
                        thumbnailUrl = message.thumbnailUrl,
                        width = message.width,
                        height = message.height,
                        isMine = isMine,
                        onClick = {
                            val mediaPreview = MediaPreview(
                                fileUrl = message.fileUrl,
                                fileName = message.fileName,
                                mimeType = message.mimeType,
                                sentAt = message.createdAt.toRelativeString(),
                                avatarUrl = sender?.avatarUrl,
                                displayName = sender?.fullName.orEmpty()
                            )
                            onVideoClick(mediaPreview)
                        }
                    )
                }

                is Message.File -> {
                    prevMessage?.let {
                        if(prevMessage.senderId != message.senderId) {
                            Spacer(modifier = Modifier.height(AppDimens.Space.Md))
                        } else {
                            Spacer(modifier = Modifier.height(AppDimens.Space.Xs))
                        }
                    }


                    FileMessageItem(
                        fileName = message.fileName,
                        fileSize = message.fileSize,
                        mimeType = message.mimeType,
                        isMine = true,
                        onDownloadClick = {
                            onDownloadFile(message)
                        },
                        onClick = {
                            onOpenFile(message)
                        }
                    )
                }
            }
        }
    }
}