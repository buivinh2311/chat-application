package com.example.infrastructure.di.core

import com.example.core_network.api.CloudinaryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudinaryModule {
    @Provides
    @Singleton
    fun provideCloudinaryRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(
                "https://api.cloudinary.com/v1_1/q11fqsai/"
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

    }

    @Provides
    @Singleton
    fun provideCloudinaryApi(
        retrofit: Retrofit
    ): CloudinaryApi {
        return retrofit.create(
            CloudinaryApi::class.java
        )
    }
}