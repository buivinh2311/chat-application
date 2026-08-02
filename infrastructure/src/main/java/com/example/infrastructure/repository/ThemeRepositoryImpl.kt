package com.example.infrastructure.repository

import com.example.core_domain.repository.ThemeRepository
import com.example.core_model.enum.DarkModeState
import com.example.infrastructure.source.theme.ThemePreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val themePreferencesDataSource: ThemePreferencesDataSource
): ThemeRepository {
    override fun getDarkModeStatus(): Flow<DarkModeState> {
        return themePreferencesDataSource.getDarkModeStatus()
    }

    override suspend fun setDarkModeStatus(status: DarkModeState) {
        themePreferencesDataSource.saveDarkModeStatus(status)
    }

}