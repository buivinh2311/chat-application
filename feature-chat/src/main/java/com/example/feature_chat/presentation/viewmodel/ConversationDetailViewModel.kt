package com.example.feature_chat.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_ui.state.UiState
import com.example.feature_chat.domain.model.FileInfo
import com.example.feature_chat.presentation.event.ConversationDetailEvent
import com.example.feature_chat.presentation.uistate.ConversationDetailData
import com.example.feature_chat.domain.usecase.DownloadFileUseCase
import com.example.feature_chat.domain.usecase.DownloadImageUseCase
import com.example.feature_chat.domain.usecase.DownloadVideoUseCase
import com.example.feature_chat.domain.usecase.GetConversationByIdUseCase
import com.example.feature_chat.domain.usecase.GetCurrentUserIdUseCase
import com.example.feature_chat.domain.usecase.ObserveMessagesByIdUseCase
import com.example.feature_chat.domain.usecase.ObserveFriendshipUseCase
import com.example.feature_chat.domain.usecase.LoadMoreMessagesUseCase
import com.example.feature_chat.domain.usecase.MarkMessageAsReadUseCase
import com.example.feature_chat.domain.usecase.ObserveFriendRequestUseCase
import com.example.feature_chat.domain.usecase.OpenFileUseCase
import com.example.feature_chat.domain.usecase.RecallFriendRequestUseCase
import com.example.feature_chat.domain.usecase.SendFileMessageUseCase
import com.example.feature_chat.domain.usecase.SendFriendRequestUseCase
import com.example.feature_chat.domain.usecase.SendImageMessageUseCase
import com.example.feature_chat.domain.usecase.SendTextMessageUseCase
import com.example.feature_chat.domain.usecase.SendVideoMessageUseCase
import com.example.feature_chat.presentation.uistate.ConversationDetailUiState
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationDetailViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val observeFriendRequestUseCase: ObserveFriendRequestUseCase,
    private val sendFriendRequestUseCase: SendFriendRequestUseCase,
    private val getConversationByIdUseCase: GetConversationByIdUseCase,
    private val recallFriendRequestUseCase: RecallFriendRequestUseCase,
    private val observeFriendshipUseCase: ObserveFriendshipUseCase,
    private val observeMessagesByIdUseCase: ObserveMessagesByIdUseCase,
    private val loadMoreMessagesUseCase: LoadMoreMessagesUseCase,
    private val sendTextMessageUseCase: SendTextMessageUseCase,
    private val sendImageMessageUseCase: SendImageMessageUseCase,
    private val sendVideoMessageUseCase: SendVideoMessageUseCase,
    private val sendFileMessageUseCase: SendFileMessageUseCase,
    private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val downloadVideoUseCase: DownloadVideoUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val openFileUseCase: OpenFileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ConversationDetailUiState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ConversationDetailEvent>()
    val event = _event.asSharedFlow()

    fun loadConversation(conversationId: String) {
        viewModelScope.launch {
            val currentUserId = getCurrentUserIdUseCase()
                .getOrElse {
                    _uiState.value = UiState.Error
                    return@launch
                }
            getConversationByIdUseCase(conversationId)
                .onSuccess { data ->
                    when (data) {
                        is ConversationDetailData.Direct -> {
                            observeDirectConversation(data, currentUserId)
                        }

                        is ConversationDetailData.Group -> {
                            observeGroupConversation(data, currentUserId)
                        }
                    }
                }
                .onFailure {
                    _uiState.value = UiState.Error
                }
        }
    }

    private suspend fun observeDirectConversation(
        data: ConversationDetailData.Direct,
        currentUserId: String
    ) {
        combine(
            observeFriendshipUseCase(data.user.id),
            observeFriendRequestUseCase(data.user.id),
            observeMessagesByIdUseCase(data.conversation.id)
        ) { isFriend, isSentRequest, messages ->
            ConversationDetailUiState(
                conversationDetailData = data,
                currentUserId = currentUserId,
                isConnected = isFriend,
                isSentRequest = isSentRequest,
                messages = messages
            )
        }
            .catch {
                _uiState.value = UiState.Error
            }
            .collect { state ->
                _uiState.value = UiState.Success(state)
            }
    }

    private suspend fun observeGroupConversation(
        data: ConversationDetailData.Group,
        currentUserId: String
    ) {
        combine(
//            observeFriendshipUseCase(data.group.id),
//            observeFriendRequestUseCase(data.group.id),
            observeMessagesByIdUseCase(data.conversation.id)
        ) { messages ->
            ConversationDetailUiState(
                conversationDetailData = data,
                currentUserId = currentUserId,
                isConnected = false,
                isSentRequest = false,
                messages = messages[0]
            )
        }
            .catch {
                _uiState.value = UiState.Error
            }
            .collect { state ->
                _uiState.value = UiState.Success(state)
            }
    }

    fun sendFriendRequest(receiverId: String) {
        viewModelScope.launch {
            sendFriendRequestUseCase(receiverId)
                .onFailure {
                    _event.emit(ConversationDetailEvent.ShowError)
                }
        }
    }

    fun recallFriendRequest(receiverId: String) {
        viewModelScope.launch {
            recallFriendRequestUseCase(receiverId)
                .onFailure {
                    _event.emit(ConversationDetailEvent.ShowError)
                }
        }
    }

    fun loadMoreMessage(
        conversationId: String,
        lastVisibleTimestamp: Timestamp
    ) {
        viewModelScope.launch {
            val messages = loadMoreMessagesUseCase(conversationId, lastVisibleTimestamp)
        }
    }

    fun sendTextMessage(
        conversationId: String,
        text: String
    ) {
        viewModelScope.launch {
            sendTextMessageUseCase(conversationId, text)
                .onFailure {
                    _event.emit(ConversationDetailEvent.ShowError)
                }
        }
    }

    fun sendMediaMessage(
        conversationId: String,
        fileInfos: List<FileInfo>
    ) {
        viewModelScope.launch {
            fileInfos.forEach { fileInfo ->
                when {
                    fileInfo.mimeType.startsWith("image/") -> {
                        sendImageMessageUseCase(conversationId, fileInfo)
                            .onFailure {
                                _event.emit(ConversationDetailEvent.ShowError)
                            }
                    }

                    fileInfo.mimeType.startsWith("video/") -> {
                        sendVideoMessageUseCase(conversationId, fileInfo)
                            .onFailure {
                                _event.emit(ConversationDetailEvent.ShowError)
                            }
                    }
                }
            }
        }
    }

    fun sendFileMessage(
        conversationId: String,
        fileInfos: List<FileInfo>
    ) {
        viewModelScope.launch {
            fileInfos.forEach { fileInfo ->
                sendFileMessageUseCase(conversationId, fileInfo)
            }
        }
    }

    fun markMessageAsRead(
        conversationId: String,
        messageId: String
    ) {
        viewModelScope.launch {
            markMessageAsReadUseCase(conversationId, messageId)
        }
    }

    fun downloadImage(
        context: Context,
        imageUrl: String,
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            downloadImageUseCase(
                context = context,
                imageUrl = imageUrl,
                fileName = fileName,
                mimeType = mimeType
            )
                .onSuccess {
                    _event.emit(ConversationDetailEvent.DownloadSuccess)
                }
                .onFailure {
                    _event.emit(ConversationDetailEvent.DownloadFailed)
                }
        }
    }

    fun downloadVideo(
        context: Context,
        videoUrl: String,
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            downloadVideoUseCase(
                context = context,
                videoUrl = videoUrl,
                fileName = fileName,
                mimeType = mimeType
            )
                .onSuccess {
                    _event.emit(ConversationDetailEvent.DownloadSuccess)
                }
                .onFailure {
                    _event.emit(ConversationDetailEvent.DownloadFailed)
                }
        }
    }

    fun downloadFile(
        context: Context,
        fileUrl: String,
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            downloadFileUseCase(
                context = context,
                fileUrl = fileUrl,
                fileName = fileName,
                mimeType = mimeType
            )
                .onSuccess {
                    _event.emit(ConversationDetailEvent.DownloadSuccess)
                }
                .onFailure {
                    _event.emit(ConversationDetailEvent.DownloadFailed)
                }
        }
    }

    fun openFile(
        context: Context,
        fileName: String,
        mimeType: String
    ) {
        viewModelScope.launch {
            openFileUseCase(
                context = context,
                fileName = fileName,
                mimeType = mimeType
            )
                .onFailure {
                    _event.emit(ConversationDetailEvent.OpenFileFailed)
                }
        }
    }
}