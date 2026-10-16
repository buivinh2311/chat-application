package com.example.feature_conversation.presentation.conversationdetail

import android.R.id.message
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipData.newPlainText
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_model.Message
import com.example.core_model.ReplyPreview
import com.example.core_model.composite.MediaPreview
import com.example.core_model.enum.MessageType
import com.example.core_resource.R
import com.example.core_ui.component.ErrorScreen
import com.example.core_ui.component.LoadingScreen
import com.example.core_ui.component.showToast
import com.example.core_ui.state.UiState
import com.example.feature_conversation.domain.model.ConversationDetailAction
import com.example.feature_conversation.domain.model.MessageAction
import com.example.feature_conversation.presentation.conversationdetail.component.ConversationDetailBottomBar
import com.example.feature_conversation.presentation.conversationdetail.component.ConversationDetailContent
import com.example.feature_conversation.presentation.conversationdetail.component.ConversationDetailTopBar
import com.example.feature_conversation.presentation.conversationdetail.component.ImagePreviewDialog
import com.example.feature_conversation.presentation.conversationdetail.component.VideoPreviewDialog
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun ConversationDetailScreen(
    conversationId: String,
    onBack: () -> Unit,
    onOption: () -> Unit,
    onOpenCamera: () -> Unit
) {
    val conversationDetailViewModel: ConversationDetailViewModel = hiltViewModel()
    val uiState by conversationDetailViewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(conversationId) {
        conversationDetailViewModel.loadConversation(conversationId)
    }

    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val coroutineScope = rememberCoroutineScope()
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

    var replyPreview: ReplyPreview? by remember {
        mutableStateOf(null)
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(replyPreview) {
        if (replyPreview != null) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    val onAction: (ConversationDetailAction) -> Unit = { action ->
        when(action) {
            // Media
            is ConversationDetailAction.DownloadImage -> {
                val imageMessage = action.imageMessage
                conversationDetailViewModel.downloadImage(
                    context = context,
                    imageUrl = imageMessage.fileUrl,
                    fileName = imageMessage.fileName,
                    mimeType = imageMessage.mimeType
                )
            }

            is ConversationDetailAction.DownloadVideo -> {
                val videoMessage = action.videoMessage
                conversationDetailViewModel.downloadVideo(
                    context = context,
                    videoUrl = videoMessage.fileUrl,
                    fileName = videoMessage.fileName,
                    mimeType = videoMessage.mimeType
                )
            }

            is ConversationDetailAction.FileSelected -> {
                conversationDetailViewModel.sendFileMessage(
                    conversationId,
                    action.fileInfos
                )
            }

            is ConversationDetailAction.MediaSelected -> {
                conversationDetailViewModel.sendMediaMessage(
                    conversationId,
                    action.fileInfos
                )
            }

            is ConversationDetailAction.OpenMedia -> {
                mediaPreview = action.mediaPreview
            }

            is ConversationDetailAction.DownloadFile -> {
                val fileMessage = action.fileMessage
                conversationDetailViewModel.downloadFile(
                    context = context,
                    fileUrl = fileMessage.fileUrl,
                    fileName = fileMessage.fileName,
                    mimeType = fileMessage.mimeType
                )
            }

            is ConversationDetailAction.OpenFile -> {
                val fileMessage = action.fileMessage
                conversationDetailViewModel.openFile(
                    context = context,
                    fileName = fileMessage.fileName,
                    mimeType = fileMessage.mimeType
                )
            }

            // Message
            is ConversationDetailAction.SendMessage -> {
                conversationDetailViewModel.sendTextMessage(
                    conversationId,
                    action.message
                )
            }

            is ConversationDetailAction.SendReplyMessage -> {
                conversationDetailViewModel.replyMessage(
                    conversationId = conversationId,
                    text = action.text,
                    replyPreview = action.replyPreview
                )
                replyPreview = null
            }

            is ConversationDetailAction.ReactToMessage -> {
                conversationDetailViewModel.reactToMessage(
                    conversationId = conversationId,
                    messageId = action.messageId,
                    emoji = action.emoji
                )
            }

            is ConversationDetailAction.RemoveReaction -> {
                conversationDetailViewModel.removeReaction(
                    conversationId = conversationId,
                    messageId = action.messageId
                )
            }
            is ConversationDetailAction.Reply -> {
                val message = action.message
                replyPreview = ReplyPreview(
                    messageId = message.id,
                    senderId = message.senderId,
                    senderName = action.senderName,
                    type = when (message) {
                        is Message.Text -> MessageType.TEXT
                        is Message.File -> MessageType.FILE
                        is Message.Video -> MessageType.VIDEO
                        is Message.Image -> MessageType.IMAGE
                    },
                    mimeType = when(message) {
                        is Message.Text -> null
                        is Message.File -> message.mimeType
                        is Message.Image -> message.mimeType
                        is Message.Video -> message.mimeType
                    },
                    contentPreview = when (message) {
                        is Message.Text -> message.text
                        is Message.File -> message.fileName
                        is Message.Video -> message.thumbnailUrl
                        is Message.Image -> message.fileUrl
                    }
                )
            }

            is ConversationDetailAction.CloseReplyPreview -> {
                replyPreview = null
            }

            is ConversationDetailAction.Copy -> {
                coroutineScope.launch {
                    clipboard.setClipEntry(
                        ClipEntry(
                            newPlainText("message", action.text)
                        )
                    )
                }
            }

            // Request
            is ConversationDetailAction.SendFriendRequest -> {

            }

            is ConversationDetailAction.RecallFriendRequest -> {

            }

            is ConversationDetailAction.StickerSelected -> {

            }
        }
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
            val conversationDetailData = conversationUiState.conversationDetailData
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    ConversationDetailTopBar(
                        imageUrl = when (conversationDetailData) {
                            is ConversationDetailData.Direct
                                -> conversationDetailData.user.avatarUrl

                            is ConversationDetailData.Group
                                -> conversationDetailData.group.avatarUrl
                        },
                        name = when (conversationDetailData) {
                            is ConversationDetailData.Direct
                                -> conversationDetailData.user.fullName

                            is ConversationDetailData.Group
                                -> conversationDetailData.group.name
                        },
                        onBack = onBack,
                        onOption = onOption
                    )
                },
                bottomBar = {
                    ConversationDetailBottomBar(
                        context = context,
                        replyPreview = replyPreview,
                        focusRequester = focusRequester,
                        onAction = onAction,
                        onOpenCamera = onOpenCamera
                    )
                }
            ) { innerPadding ->
                ConversationDetailContent(
                    data = conversationDetailData,
                    currentUser = conversationUiState.currentUser,
                    isConnected = false,
                    isSentRequest = true,
                    messages = messages,
                    innerPadding = innerPadding,
                    onAction = onAction
                )
            }

            mediaPreview?.let { preview ->
                val message = messages.find { it.id == preview.messageId }
                when (message) {
                    is Message.Image -> {
                        ImagePreviewDialog(
                            imageMessage = message,
                            imagePreview = preview,
                            onDismiss = {
                                mediaPreview = null
                            },
                            onAction = onAction
                        )
                    }

                    is Message.Video -> {
                        VideoPreviewDialog(
                            videoMessage = message,
                            videoPreview = preview,
                            onDismiss = {
                                mediaPreview = null
                            },
                            onAction = onAction
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}