package com.example.infrastructure.source.theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core_model.enum.DarkModeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemePreferencesDataSourceImpl @Inject constructor(
    context: Context
): ThemePreferencesDataSource {
    private val dataStore = context.dataStore
    private val darkThemeKey = stringPreferencesKey("dark_theme")
    override fun getDarkModeStatus(): Flow<DarkModeState> {
        return dataStore.data.map { preferences ->
            DarkModeState.valueOf(
                preferences[darkThemeKey] ?: DarkModeState.DEFAULT.name
            )
        }
    }

    override suspend fun saveDarkModeStatus(status: DarkModeState) {
        dataStore.edit { preferences ->
            preferences[darkThemeKey] = status.name
        }
    }
}