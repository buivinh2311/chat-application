package com.example.infrastructure.di.core

import android.content.Context
import com.cloudinary.android.MediaManager
import com.example.core_utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudinaryModule {
    @Provides
    @Singleton
    fun provideMediaManager(
        @ApplicationContext context: Context
    ): MediaManager {
        MediaManager.init(
            context,
            mapOf(
                "cloud_name" to AppConstants.CLOUD_NAME,
                "secure" to true
            )
        )
        return MediaManager.get()
    }
}