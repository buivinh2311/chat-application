package com.example.infrastructure.di.theme

import android.content.Context
import com.example.infrastructure.source.theme.ThemePreferencesDataSource
import com.example.infrastructure.source.theme.ThemePreferencesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ThemePreferencesDataSourceModule{
    @Provides
    fun provideThemeLocalDataSource(
        @ApplicationContext context: Context
    ): ThemePreferencesDataSource {
        return ThemePreferencesDataSourceImpl(context)
    }
}