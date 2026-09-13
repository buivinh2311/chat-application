package com.example.feature_chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.User
import com.example.core_ui.state.UiState
import com.example.feature_chat.domain.usecase.CreateGroupUseCase
import com.example.feature_chat.domain.usecase.GetConversationByIdUseCase
import com.example.feature_chat.domain.usecase.UploadGroupAvatarUseCase
import com.example.feature_chat.presentation.event.ConversationDetailEvent
import com.example.feature_chat.presentation.uistate.ConversationDetailData
import com.example.feature_chat.presentation.uistate.ConversationOptionUiState
import com.example.feature_chat.presentation.uistate.ConversationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConversationOptionViewModel @Inject constructor(
    private val getConversationByIdUseCase: GetConversationByIdUseCase,
    private val uploadGroupAvatarUseCase: UploadGroupAvatarUseCase,
    private val createGroupUseCase: CreateGroupUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ConversationOptionUiState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ConversationDetailEvent>()
    val event = _event.asSharedFlow()

    fun loadConversation(conversationId: String) {
        viewModelScope.launch {
            getConversationByIdUseCase(conversationId)
                .onSuccess { data ->
                    _uiState.value = UiState.Success(
                        ConversationOptionUiState(data)
                    )
                }
                .onFailure {
                    _uiState.value = UiState.Error
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
