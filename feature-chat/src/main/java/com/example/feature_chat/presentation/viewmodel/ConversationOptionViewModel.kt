package com.example.feature_chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.User
import com.example.core_ui.state.UiState
import com.example.feature_chat.domain.model.FileInfo
import com.example.feature_chat.domain.usecase.CreateGroupUseCase
import com.example.feature_chat.domain.usecase.GetConversationUseCase
import com.example.feature_chat.domain.usecase.UploadGroupAvatarUseCase
import com.example.feature_chat.presentation.event.ConversationDetailEvent
import com.example.feature_chat.presentation.screen.ConversationDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.collections.forEach

@HiltViewModel
class ConversationOptionViewModel @Inject constructor(
    private val getConversationUseCase: GetConversationUseCase,
    private val uploadGroupAvatarUseCase: UploadGroupAvatarUseCase,
    private val createGroupUseCase: CreateGroupUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ConversationDetailData>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ConversationDetailEvent>()
    val event = _event.asSharedFlow()

    fun loadConversation(conversationId: String) {
        viewModelScope.launch {
            val result = getConversationUseCase(conversationId)
            _uiState.value = if (result == null) {
                UiState.Empty
            } else {
                UiState.Success(result)
            }
        }
    }

    fun uploadGroupAvatar(
        conversationId: String,
        file: File
    ) {
        viewModelScope.launch {
            uploadGroupAvatarUseCase(conversationId, file)
        }
    }

    fun createGroup(
        name: String,
        user2: User
    ) {
        viewModelScope.launch {
            createGroupUseCase(name, user2)
        }
    }
}
