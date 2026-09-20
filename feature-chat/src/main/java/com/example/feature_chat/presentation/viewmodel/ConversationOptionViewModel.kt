package com.example.feature_chat.presentation.viewmodel

import android.R
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_ui.state.UiState
import com.example.feature_chat.domain.usecase.ChangeGroupAvatarUseCase
import com.example.feature_chat.domain.usecase.ChangeGroupNameUseCase
import com.example.feature_chat.domain.usecase.GetConversationByIdUseCase
import com.example.feature_chat.domain.usecase.ObserveConversationByIdUseCase
import com.example.feature_chat.presentation.event.ConversationOptionEvent
import com.example.feature_chat.presentation.uistate.ConversationOptionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConversationOptionViewModel @Inject constructor(
    private val observeConversationByIdUseCase: ObserveConversationByIdUseCase,
    private val changeGroupAvatarUseCase: ChangeGroupAvatarUseCase,
    private val changeGroupNameUseCase: ChangeGroupNameUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ConversationOptionUiState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ConversationOptionEvent>()
    val event = _event.asSharedFlow()

    fun loadConversation(conversationId: String) {
        viewModelScope.launch {
            observeConversationByIdUseCase(conversationId)
                .catch {
                    _event.emit(ConversationOptionEvent.ShowError)
                }
                .collect { data ->
                    _uiState.value = UiState.Success(
                        ConversationOptionUiState(
                            conversationDetailData = data
                        )
                    )
                }
        }
    }

    fun changeGroupAvatar(
        groupId: String,
        file: File
    ) {
        viewModelScope.launch {
            changeGroupAvatarUseCase(groupId, file)
                .onFailure {
                    _event.emit(ConversationOptionEvent.ShowError)
                }
        }
    }

    fun changeGroupName(
        groupId: String,
        groupName: String
    ) {
        viewModelScope.launch {
            changeGroupNameUseCase(groupId, groupName)
                .onFailure {
                    _event.emit(ConversationOptionEvent.ShowError)
                }
        }
    }
}
