package com.example.chatapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.DarkModeState
import com.example.core_ui.state.UiState
import com.example.feature_settings.usecase.GetThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase
): ViewModel() {
    private val _themeState = MutableStateFlow<UiState<DarkModeState>> (
        UiState.Loading
    )
    val themeState = _themeState.asStateFlow()

    init {
        observerThemeState()
    }

    private fun observerThemeState() {
        viewModelScope.launch {
            getThemeModeUseCase().collect { state ->
                _themeState.value = UiState.Success(state)
            }
        }
    }
}