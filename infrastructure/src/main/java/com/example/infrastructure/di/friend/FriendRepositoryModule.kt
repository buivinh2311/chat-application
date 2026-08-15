package com.example.infrastructure.di.friend

import com.example.core_domain.repository.FriendRepository
import com.example.infrastructure.repository.FriendRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FriendRepositoryModule {
    @Binds
    abstract fun bindFriendRepository(
        impl: FriendRepositoryImpl
    ): FriendRepository
}