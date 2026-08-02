package com.example.chatapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.enum.DarkModeState
import com.example.core_ui.state.UiState
import com.example.feature_settings.usecase.GetThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getThemeModeUseCase: GetThemeModeUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {
    private val _themeState = MutableStateFlow<UiState<DarkModeState>> (
        UiState.Loading
    )
    val themeState = _themeState.asStateFlow()

    private val _startupState = MutableStateFlow<StartupState>(StartupState.Loading)
    val startupState = _startupState.asStateFlow()

    init {
        observerThemeState()
        checkLogin()
    }

    private fun observerThemeState() {
        viewModelScope.launch {
            getThemeModeUseCase().collect { state ->
                _themeState.value = UiState.Success(state)
            }
        }
    }

    private fun checkLogin() {
        viewModelScope.launch {
            delay(1000)
            val user = getCurrentUserUseCase()
            _startupState.value =
                when {
                    user == null ->
                        StartupState.Login

                    user.isEmailVerified ->
                        StartupState.Chat

                    else ->
                        StartupState.Login
                }
        }
    }
}