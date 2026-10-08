package com.example.feature_conversation.presentation.conversationdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Message
import com.example.core_model.composite.MediaPreview
import com.example.core_resource.R
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.core_utils.isAtBottom
import com.example.core_utils.isScrolled
import com.example.feature_conversation.domain.model.FileInfo
import com.example.feature_conversation.presentation.conversationdetail.component.ConversationDetailBottomBar
import com.example.feature_conversation.presentation.conversationdetail.component.ConversationDetailDirect
import com.example.feature_conversation.presentation.conversationdetail.component.ConversationDetailGroup
import com.example.feature_conversation.presentation.conversationdetail.component.ConversationDetailTopBar
import com.example.feature_conversation.presentation.conversationdetail.component.ImagePreviewDialog
import com.example.feature_conversation.presentation.conversationdetail.component.VideoPreviewDialog

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ConversationDetailScreen(
    conversationId: String,
    onBackClick: () -> Unit,
    onOptionClick: () -> Unit,
    onOpenCamera: () -> Unit
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

    var mediaPreview: MediaPreview? by remember {
        mutableStateOf(null)
    }
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf { listState.isScrolled }
    }
    val isAtBottom by remember {
        derivedStateOf { listState.isAtBottom }
    }

    val onSendMessageClick: (String) -> Unit = { message ->
        conversationDetailViewModel.sendTextMessage(conversationId, message)
    }
    val onFileSelected: (List<FileInfo>) -> Unit = { fileInfos ->
        conversationDetailViewModel.sendFileMessage(conversationId, fileInfos)
    }
    val onMediaSelected: (List<FileInfo>) -> Unit = { fileInfos ->
        conversationDetailViewModel.sendMediaMessage(conversationId, fileInfos)
    }
    val onMediaClick: (MediaPreview) -> Unit = { preview ->
        mediaPreview = preview
    }
    val onDownloadFile: (Message.File) -> Unit = { file ->
        conversationDetailViewModel.downloadFile(
            context = context,
            fileUrl = file.fileUrl,
            fileName = file.fileName,
            mimeType = file.mimeType
        )
    }
    val onOpenFile: (Message.File) -> Unit = { file ->
        conversationDetailViewModel.openFile(
            context = context,
            fileName = file.fileName,
            mimeType = file.mimeType
        )
    }
    val onReactToMessage: (String, String) -> Unit = { messageId, emoji ->
        conversationDetailViewModel.reactToMessage(
            conversationId = conversationId,
            messageId = messageId,
            emoji = emoji
        )
    }
    val onRemoveReaction: (String) -> Unit = { messageId ->
        conversationDetailViewModel.removeReaction(
            conversationId = conversationId,
            messageId = messageId
        )
    }

    when (val state = uiState) {
        UiState.Loading -> {
            LoadingScreen()
        }

        UiState.Error -> {
            ErrorScreen(title = "error")
        }

        UiState.Empty -> {

        }

        is UiState.Success -> {
            val conversationUiState = state.data
            val messages = conversationUiState.messages
            when(val data = conversationUiState.conversationDetailData) {
                is ConversationDetailData.Direct -> {
                    val user = data.user
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            ConversationDetailTopBar(
                                imageUrl = user.avatarUrl,
                                name = user.fullName,
                                isScrolled = isScrolled,
                                onBackClick = onBackClick,
                                onOptionClick = onOptionClick
                            )
                        },
                        bottomBar = {
                            ConversationDetailBottomBar(
                                showDivider = !isAtBottom,
                                onSendMessageClick = onSendMessageClick,
                                onLikeClick = {

                                },
                                onFileSelected = onFileSelected,
                                onOpenCamera = onOpenCamera,
                                onMediaSelected = onMediaSelected,
                                onStickerClick = { }
                            )
                        }
                    ) { innerPadding ->
                        ConversationDetailDirect(
                            user = user,
                            currentUser = conversationUiState.currentUser,
                            isConnected = conversationUiState.isConnected,
                            isSentRequest = conversationUiState.isSentRequest,
                            messages = messages,
                            innerPadding = innerPadding,
                            onSendFriendRequest = { userId ->
                                conversationDetailViewModel.sendFriendRequest(userId)
                            },
                            onRecallFriendRequest = { userId ->
                                conversationDetailViewModel.recallFriendRequest(userId)
                            },
                            onMediaClick = onMediaClick,
                            onDownloadFile = onDownloadFile,
                            onOpenFile = onOpenFile,
                            onReactToMessage = onReactToMessage,
                            onRemoveReaction = onRemoveReaction
                        )
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
                                isScrolled = isScrolled,
                                onBackClick = onBackClick,
                                onOptionClick = onOptionClick
                            )
                        },
                        bottomBar = {
                            ConversationDetailBottomBar(
                                showDivider = !isAtBottom,
                                onSendMessageClick = onSendMessageClick,
                                onLikeClick = { },
                                onFileSelected = onFileSelected,
                                onOpenCamera = onOpenCamera,
                                onMediaSelected = onMediaSelected,
                                onStickerClick = {}
                            )
                        }
                    ) { innerPadding ->
                        ConversationDetailGroup(
                            group = group,
                            currentUser = conversationUiState.currentUser,
                            members = data.users,
                            messages = messages,
                            innerPadding = innerPadding,
//                            onSendFriendRequest = { userId ->
//                                conversationDetailViewModel.sendFriendRequest(userId)
//                            },
//                            onRecallFriendRequest = { userId ->
//                                conversationDetailViewModel.recallFriendRequest(userId)
//                            },
                            onMediaClick = onMediaClick,
                            onDownloadFile = onDownloadFile,
                            onOpenFile = onOpenFile,
                            onReactToMessage = onReactToMessage,
                            onRemoveReaction = onRemoveReaction
                        )
                    }
                }
            }

            mediaPreview?.let { preview ->
                val message = messages.find { it.id == preview.messageId }
                when(message) {
                    is Message.Image -> {
                        ImagePreviewDialog(
                            imageMessage = message,
                            imagePreview = preview,
                            onDismiss = {
                                mediaPreview = null
                            },
                            onDownload = {
                                conversationDetailViewModel.downloadImage(
                                    context = context,
                                    imageUrl = message.fileUrl,
                                    fileName = message.fileName,
                                    mimeType = message.mimeType
                                )
                            },
                            onReactToMessage = { emoji ->
                                onReactToMessage(message.id, emoji)
                            },
                            onRemoveReaction = {
                                onRemoveReaction(message.id)
                            }
                        )
                    }

                    is Message.Video -> {
                        VideoPreviewDialog(
                            videoMessage = message,
                            videoPreview = preview,
                            onDismiss = {
                                mediaPreview = null
                            },
                            onDownload = {
                                conversationDetailViewModel.downloadImage(
                                    context = context,
                                    imageUrl = message.fileUrl,
                                    fileName = message.fileName,
                                    mimeType = message.mimeType
                                )
                            },
                            onReactToMessage = { emoji ->
                                onReactToMessage(message.id, emoji)
                            },
                            onRemoveReaction = {
                                onRemoveReaction(message.id)
                            }
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}