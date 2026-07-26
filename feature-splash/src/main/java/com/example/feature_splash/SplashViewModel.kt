package com.example.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
): ViewModel() {
    private val _event = MutableSharedFlow<SplashEvent>()
    val event = _event.asSharedFlow()

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            delay(2000)
            val user = getCurrentUserUseCase()
            when {
                user == null -> {
                    _event.emit(SplashEvent.NavigateToLogin)
                }

                user.isEmailVerified -> {
                    _event.emit(SplashEvent.NavigateToChat)
                }

                else -> {
                    _event.emit(SplashEvent.NavigateToLogin)
                }
            }
        }
    }
}