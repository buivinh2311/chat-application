package com.example.feature_settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.Gender
import com.example.feature_settings.presentation.event.ProfileEvent
import com.example.feature_settings.presentation.state.ProfileUiState
import com.example.feature_settings.usecase.GetUserUseCase
import com.example.feature_settings.usecase.UpdateProfileUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event = _event.asSharedFlow()

    val currentUser = getUserUseCase()

    fun updateProfile(
        uid: String,
        avatarUrl: String,
        firstName: String,
        lastName: String,
        address: String,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ) {
        viewModelScope.launch {
            val isFirstNameBlank = firstName.isBlank()
            val isLastNameBlank = lastName.isBlank()
            _uiState.update {
                it.copy(
                    isLastNameBlank = isLastNameBlank,
                    isFirstNameBlank = isFirstNameBlank
                )
            }
            if (isLastNameBlank || isFirstNameBlank) {
                return@launch
            }
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = updateProfileUseCase(
                uid = uid,
                avatarUrl = avatarUrl.ifBlank { null },
                firstName = firstName,
                lastName = lastName,
                address = address.ifBlank{ null },
                dateOfBirth = dateOfBirth,
                gender = gender
            )
            if(result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isUpdateSuccess = true,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isUpdateSuccess = false,
                        isLoading = false
                    )
                }
            }
            _event.emit(ProfileEvent.ShowBanner)
        }
    }
}