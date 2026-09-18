package com.example.feature_chat.presentation.component

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.core_model.Message
import com.example.core_model.User
import com.example.core_model.composite.MediaPreview
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppButton
import com.example.core_utils.toRelativeString
import com.example.share_presentation.FileMessageItem
import com.example.share_presentation.ImageMessageItem
import com.example.share_presentation.TextMessageItem
import com.example.share_presentation.VideoMessageItem

@Composable
fun ConversationDetailDirect(
    user: User,
    isConnected: Boolean,
    isSentRequest: Boolean,
    messages: List<Message>,
    innerPadding: PaddingValues,
    onSendFriendRequest: (String) -> Unit,
    onRecallFriendRequest: (String) -> Unit,
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
            top = AppDimens.Space.Xxl,
            bottom = AppDimens.Space.Lg
        )
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = user.avatarUrl,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.account),
                    error = painterResource(R.drawable.account),
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .aspectRatio(1f)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            if(isConnected) {
                Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AppButton(
                        modifier = Modifier.fillMaxWidth(0.35f),
                        label = if(!isSentRequest)
                            stringResource(R.string.add_friend)
                        else
                            stringResource(R.string.recall),
                        color = if(!isSentRequest)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        onClick = {
                            if(isSentRequest) {
                                onSendFriendRequest(user.id)
                            } else {
                                onRecallFriendRequest(user.id)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(AppDimens.Space.Xxl))
        }

        items(
            count = messages.size,
            key = { index -> messages[index].id }
        ) { index ->
            val message = messages[index]
            val prevMessage = if(index != 0) messages[index - 1] else null
            val isMine = message.senderId != user.id
            when (message) {
                is Message.Text -> {
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
                                avatarUrl = user.avatarUrl,
                                displayName = user.firstName + " " + user.lastName
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
                                avatarUrl = user.avatarUrl,
                                displayName = user.firstName + " " + user.lastName
                            )
                            onVideoClick(mediaPreview)
                        }
                    )
                }

                is Message.File -> {
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