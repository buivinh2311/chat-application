package com.example.infrastructure.di.account

import com.example.core_domain.repository.StorageRepository
import com.example.infrastructure.repository.StorageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageRepositoryModule {
    @Binds
    abstract fun bindStorageRepository(
        impl: StorageRepositoryImpl
    ): StorageRepository
}