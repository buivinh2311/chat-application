package com.example.feature_auth.presentation.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.result.RegisterResult
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
        fullName: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {
        viewModelScope.launch {
            val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$")
            val isFullNameBlank = fullName.isBlank()
            val isEmailBlank = email.isBlank()
            val isPasswordBlank = password.isBlank()
            val isRepeatPasswordBlank = repeatPassword.isBlank()
            val isEmailTypeError = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isNotStrongPassword = !passwordRegex.matches(password)
            val isRepeatPasswordError = repeatPassword != password
            _uiState.update {
                it.copy(
                    isFullNameBlank = isFullNameBlank,
                    isEmailBlank = isEmailBlank,
                    isPasswordBlank = isPasswordBlank,
                    isRepeatPasswordBlank = isRepeatPasswordBlank,
                    isEmailTypeError = isEmailTypeError,
                    isNotStrongPassword = isNotStrongPassword,
                    isRepeatPasswordError = isRepeatPasswordError
                )
            }

            val isInvalid = isFullNameBlank || isEmailBlank ||
                    isPasswordBlank || isRepeatPasswordBlank || isEmailTypeError ||
                    isNotStrongPassword || isRepeatPasswordError

            if(!isInvalid) {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                when(val result = registerUseCase(fullName, email, password)) {
                    is RegisterResult.Success -> {
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