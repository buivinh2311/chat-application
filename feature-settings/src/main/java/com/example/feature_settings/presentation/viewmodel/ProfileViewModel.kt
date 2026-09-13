package com.example.feature_settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.User
import com.example.core_model.enum.Gender
import com.example.core_ui.state.UiState
import com.example.feature_settings.presentation.event.ProfileEvent
import com.example.feature_settings.presentation.state.ProfileUiState
import com.example.feature_settings.usecase.ObserverUserUseCase
import com.example.feature_settings.usecase.UpdateProfileUseCase
import com.example.feature_settings.usecase.UploadAvatarUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val observerUserUseCase: ObserverUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ProfileUiState>>(
        UiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event = _event.asSharedFlow()

    fun loadCurrentUser() {
        viewModelScope.launch {
            observerUserUseCase()
                .catch {
                    _uiState.value = UiState.Error
                }
                .collect { currentUser ->
                    currentUser?.let {
                        _uiState.value = UiState.Success(
                            ProfileUiState(
                                currentUser = currentUser
                            )
                        )
                    }
                }
        }
    }

    fun uploadAvatar(
        uid: String,
        file: File
    ) {
        viewModelScope.launch {
            uploadAvatarUseCase(uid, file)
                .onFailure {
                    _event.emit(ProfileEvent.ShowError)
                }
        }
    }

    fun updateProfile(
        user: User,
        firstName: String,
        lastName: String,
        address: String,
        dateOfBirth: Timestamp?,
        gender: Gender?
    ) {
        viewModelScope.launch {
            val isFirstNameBlank = firstName.isBlank()
            val isLastNameBlank = lastName.isBlank()
            _uiState.value = UiState.Success(
                ProfileUiState(
                    currentUser = user,
                    isFirstNameBlank = isFirstNameBlank,
                    isLastNameBlank = isLastNameBlank
                )
            )
            if (isLastNameBlank || isFirstNameBlank) {
                return@launch
            }
            updateProfileUseCase(
                uid = user.id,
                firstName = firstName,
                lastName = lastName,
                address = address.ifBlank { null },
                dateOfBirth = dateOfBirth,
                gender = gender
            )
                .onSuccess {
                    _event.emit(ProfileEvent.UpdateSuccessful)
                }
                .onFailure {
                    _event.emit(ProfileEvent.ShowError)
                }
        }
    }
}