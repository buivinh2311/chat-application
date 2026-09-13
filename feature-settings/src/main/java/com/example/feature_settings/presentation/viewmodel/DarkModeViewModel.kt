package com.example.feature_settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.enum.DarkModeState
import com.example.core_ui.state.UiState
import com.example.feature_settings.usecase.GetThemeModeUseCase
import com.example.feature_settings.usecase.SetThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DarkModeViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<DarkModeState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    fun observerThemeState() {
        viewModelScope.launch {
            getThemeModeUseCase()
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect { theme ->
                    _uiState.value = UiState.Success(theme)
                }
        }
    }

    fun toggleThemeState(state: DarkModeState) {
        viewModelScope.launch {
            setThemeModeUseCase(state)
        }
    }
}