package com.example.feature_settings.usecase

import com.example.core_domain.repository.ThemeRepository
import com.example.core_model.DarkModeState
import javax.inject.Inject

class SetThemeModeUseCase @Inject constructor(
    private val repository: ThemeRepository
) {
    suspend operator fun invoke(state: DarkModeState) {
        repository.setDarkModeStatus(state)
    }
}