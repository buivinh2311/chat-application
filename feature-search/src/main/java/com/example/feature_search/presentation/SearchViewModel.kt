package com.example.feature_search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.User
import com.example.core_model.enum.ConversationType
import com.example.core_ui.state.UiState
import com.example.feature_search.usecase.GetConversationIdUseCase
import com.example.feature_search.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val getConversationIdUseCase: GetConversationIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<User>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<SearchEvent>()
    val event = _event.asSharedFlow()

    fun searchUser(keyword: String) {
        viewModelScope.launch {
            searchUseCase(keyword)
                .onSuccess { users ->
                    _uiState.value = if (users.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(users)
                    }
                }
                .onFailure {
                    _event.emit(SearchEvent.ShowError)
                }
        }
    }

    fun openDirectConversation(user2: String) {
        viewModelScope.launch {
            getConversationIdUseCase(user2)
                .onSuccess { conversationId ->
                    _event.emit(
                        SearchEvent.NavigateToConversationDetail(
                            conversationId
                        )
                    )
                }
                .onFailure {
                    _event.emit(SearchEvent.ShowError)
                }
        }
    }

    fun openGroupConversation(conversationId: String) {
        viewModelScope.launch {
            _event.emit(
                SearchEvent.NavigateToConversationDetail(
                    conversationId
                )
            )
        }
    }
}