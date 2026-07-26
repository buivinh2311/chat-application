package com.example.feature_auth.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.result.RegisterResult
import com.example.feature_auth.presentation.event.RegisterEvent
import com.example.feature_auth.presentation.state.RegisterState
import com.example.feature_auth.usecase.RegisterUseCase
import com.example.feature_auth.usecase.SendEmailVerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<RegisterEvent>()
    val event = _event.asSharedFlow()

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {
        viewModelScope.launch {
            val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")
            _uiState.update {
                it.copy(
                    isFirstNameBlank = firstName.isBlank(),
                    isLastNameBlank = lastName.isBlank(),
                    isEmailBlank = email.isBlank(),
                    isPasswordBlank = password.isBlank(),
                    isRepeatPasswordBlank = repeatPassword.isBlank(),
                    isEmailTypeError = !Patterns.EMAIL_ADDRESS.matcher(email).matches(),
                    isNotStrongPassword = !passwordRegex.matches(password),
                    isRepeatPasswordError = repeatPassword != password
                )
            }

            val state = uiState.value
            val isInvalid = state.isFirstNameBlank || state.isLastNameBlank
                    || state.isEmailBlank || state.isPasswordBlank || state.isRepeatPasswordBlank
                    || state.isEmailTypeError || state.isNotStrongPassword || state.isRepeatPasswordError

            if(!isInvalid) {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                when(val result = registerUseCase(email, password)) {
                    RegisterResult.Success -> {
                        sendEmailVerificationUseCase()
                        _event.emit(RegisterEvent.NavigateToLogin)
                    }

                    is RegisterResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result
                            )
                        }
                        _event.emit(RegisterEvent.ShowError)
                    }
                }
            }
        }
    }
}