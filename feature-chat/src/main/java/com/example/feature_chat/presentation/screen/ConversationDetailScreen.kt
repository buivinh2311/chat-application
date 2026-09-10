package com.example.feature_chat.presentation.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.core_model.Message
import com.example.core_model.composite.MediaPreview
import com.example.core_model.enum.MessageType
import com.example.core_resource.AppDimens
import com.example.core_resource.R
import com.example.core_ui.component.AppButton
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.core_utils.toRelativeString
import com.example.feature_chat.presentation.component.ConversationDetailBottomBar
import com.example.feature_chat.presentation.component.ConversationDetailTopBar
import com.example.feature_chat.presentation.component.ImagePreviewDialog
import com.example.feature_chat.presentation.component.VideoPreviewDialog
import com.example.feature_chat.presentation.event.ConversationDetailEvent
import com.example.feature_chat.presentation.event.ConversationEvent
import com.example.feature_chat.presentation.viewmodel.ConversationDetailViewModel
import com.example.share_presentation.FileMessageItem
import com.example.share_presentation.ImageMessageItem
import com.example.share_presentation.TextMessageItem
import com.example.share_presentation.VideoMessageItem
import java.io.File

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ConversationDetailScreen(
    conversationId: String,
    onBackClick: () -> Unit,
    onOptionClick: () -> Unit
) {
    val conversationDetailViewModel: ConversationDetailViewModel = hiltViewModel()
    val uiState by conversationDetailViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(conversationId) {
        conversationDetailViewModel.loadConversation(conversationId)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        conversationDetailViewModel.event.collect { event ->
            when (event) {
                ConversationDetailEvent.ShowError -> {
                    showToast(
                        context,
                        message = context.getString(R.string.text_unknown_error)
                    )
                }

                ConversationDetailEvent.DownloadSuccess -> {
                    showToast(
                        context,
                        message = context.getString(R.string.download_success)
                    )
                }

                ConversationDetailEvent.DownloadFailed -> {
                    showToast(
                        context,
                        message = context.getString(R.string.download_failed)
                    )
                }

                ConversationDetailEvent.OpenFileFailed -> {
                    showToast(
                        context,
                        message = context.getString(R.string.open_file_failed)
                    )
                }
            }
        }
    }

    var imagePreview: MediaPreview? by remember {
        mutableStateOf(null)
    }

    var videoPreview: MediaPreview? by remember {
        mutableStateOf(null)
    }

    when (val state = uiState) {
        UiState.Loading -> {

        }

        UiState.Empty -> {

        }

        is UiState.Success -> {
            when(val data = state.data) {
                is ConversationDetailData.Direct -> {
                    val user = data.user
                    val isFriend by conversationDetailViewModel
                        .isFriend(user.id)
                        .collectAsStateWithLifecycle(true)
                    val isSentRequest by conversationDetailViewModel
                        .isSentRequest(user.id)
                        .collectAsStateWithLifecycle(false)
                    val messages by conversationDetailViewModel
                        .getMessage(conversationId)
                        .collectAsStateWithLifecycle(emptyList())

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            ConversationDetailTopBar(
                                imageUrl = user.avatarUrl,
                                name = user.firstName + " " + user.lastName,
                                onBackClick = onBackClick,
                                onOptionClick = onOptionClick
                            )
                        },
                        bottomBar = {
                            ConversationDetailBottomBar(
                                onSendMessageClick = { message ->
                                    conversationDetailViewModel.sendTextMessage(conversationId, message)
                                },
                                onLikeClick = {

                                },
                                onFileSelected = { fileInfos ->
                                    conversationDetailViewModel.sendFileMessage(conversationId, fileInfos)
                                },
                                onCameraClick = { },
                                onMediaSelected = { fileInfos ->
                                    conversationDetailViewModel.sendMediaMessage(conversationId, fileInfos)
                                },
                                onEmojiClick = { }
                            )
                        }
                    ) { innerPadding ->
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

                                if(!isFriend) {
                                    Spacer(modifier = Modifier.height(AppDimens.Space.Xl))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        AppButton(
                                            modifier = Modifier.fillMaxWidth(0.35f),
                                            label = if(!isSentRequest) stringResource(R.string.add_friend)
                                            else stringResource(R.string.recall),
                                            color = if(!isSentRequest) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                            onClick = {
                                                if(!isSentRequest) {
                                                    conversationDetailViewModel
                                                        .sendFriendRequest(user.id)
                                                } else {
                                                    conversationDetailViewModel
                                                        .recallFriendRequest(user.id)
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
                                                imagePreview = MediaPreview(
                                                    fileUrl = message.fileUrl,
                                                    fileName = message.fileName,
                                                    mimeType = message.mimeType,
                                                    sentAt = message.createdAt.toRelativeString(),
                                                    avatarUrl = user.avatarUrl,
                                                    displayName = user.firstName + " " + user.lastName
                                                )
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
                                                videoPreview = MediaPreview(
                                                    fileUrl = message.fileUrl,
                                                    fileName = message.fileName,
                                                    mimeType = message.mimeType,
                                                    sentAt = message.createdAt.toRelativeString(),
                                                    avatarUrl = user.avatarUrl,
                                                    displayName = user.firstName + " " + user.lastName
                                                )
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
                                                conversationDetailViewModel.downloadFile(
                                                    context = context,
                                                    fileUrl = message.fileUrl,
                                                    fileName = message.fileName,
                                                    mimeType = message.mimeType
                                                )
                                            },
                                            onClick = {
                                                conversationDetailViewModel.openFile(
                                                    context = context,
                                                    fileName = message.fileName,
                                                    mimeType = message.mimeType
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is ConversationDetailData.Group -> {
                    val group = data.group
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            ConversationDetailTopBar(
                                imageUrl = group.avatarUrl,
                                name = group.name,
                                onBackClick = onBackClick,
                                onOptionClick = onOptionClick
                            )
                        },
                        bottomBar = {
                            ConversationDetailBottomBar(
                                onSendMessageClick = { message ->
                                    conversationDetailViewModel.sendTextMessage(conversationId, message)
                                },
                                onLikeClick = { },
                                onFileSelected = { fileInfos ->
                                    conversationDetailViewModel.sendFileMessage(conversationId, fileInfos)
                                },
                                onCameraClick = { },
                                onMediaSelected = { fileInfos ->
                                    conversationDetailViewModel.sendMediaMessage(conversationId, fileInfos)
                                },
                                onEmojiClick = { }
                            )
                        }
                    ) { innerPadding ->
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

                                AppButton(
                                    label = stringResource(R.string.send_group_request),
                                    color = MaterialTheme.colorScheme.primary,
                                    onClick = {

                                    }
                                )
                            }

//                            items(
//                                count = 30
//                            ) { index ->
//                                if (index % 2 == 0) {
//                                    Message(
//                                        content = "message",
//                                        isMine = false
//                                    )
//                                } else {
//                                    Message(
//                                        content = "message",
//                                        isMine = true
//                                    )
//                                }
//                            }
                        }
                    }
                }
            }
        }
    }

    imagePreview?.let {
        ImagePreviewDialog(
            fileUrl = it.fileUrl,
            sentAt = it.sentAt,
            avatarUrl = it.avatarUrl,
            senderName = it.displayName,
            onDismiss = {
                imagePreview = null
            },
            onDownload = {
                conversationDetailViewModel.downloadImage(
                    context = context,
                    imageUrl = it.fileUrl,
                    fileName = it.fileName,
                    mimeType = it.mimeType
                )
            }
        )
    }

    videoPreview?.let {
        VideoPreviewDialog(
            fileUrl = it.fileUrl,
            sentAt = it.sentAt,
            avatarUrl = it.avatarUrl,
            senderName = it.displayName,
            onDismiss = {
                videoPreview = null
            },
            onDownload = {
                conversationDetailViewModel.downloadVideo(
                    context = context,
                    videoUrl = it.fileUrl,
                    fileName = it.fileName,
                    mimeType = it.mimeType
                )
            }
        )
    }
}