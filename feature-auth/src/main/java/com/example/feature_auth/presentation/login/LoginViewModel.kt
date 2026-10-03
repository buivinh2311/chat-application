package com.example.feature_auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.result.LoginResult
import com.example.core_model.result.ResendResult
import com.example.feature_auth.usecase.LoginUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val isEmailBlank = email.isBlank()
            val isPasswordBlank = password.isBlank()
            _uiState.update {
                it.copy(
                    isEmailBlank = isEmailBlank,
                    isPasswordBlank = isPasswordBlank,
                    resendResult = null
                )
            }
            val isInvalid = isEmailBlank || isPasswordBlank
            if(!isInvalid) {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                when(val result = loginUseCase(email, password)) {
                    LoginResult.Success -> {
                        _uiState.update {
                            it.copy(
                                loginResult = LoginResult.Success
                            )
                        }
                        _event.emit(LoginEvent.NavigateToHome)
                    }

                    is LoginResult.Failure -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                loginResult = result
                            )
                        }
                        _event.emit(LoginEvent.ShowBanner)
                    }
                }
            }
        }
    }

    fun resendEmailVerification() {
        viewModelScope.launch {
            when(val result = sendEmailVerificationUseCase()) {
                ResendResult.Success -> {
                    _uiState.update {
                        it.copy(
                            resendResult = ResendResult.Success,
                            loginResult = null
                        )
                    }
                }

                is ResendResult.Failure -> {
                    _uiState.update {
                        it.copy(
                            resendResult = result,
                            loginResult = null
                        )
                    }
                }
            }
        }
    }
}