package com.example.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.User
import com.example.core_ui.state.UiState
import com.example.feature_search.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<User>>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun searchUser(keyword: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val users = searchUseCase(keyword)
            _uiState.value = if(users.isEmpty()) {
                UiState.Empty
            } else {
                UiState.Success(users)
            }
        }
    }
}