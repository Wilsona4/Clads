package com.decagonhq.clads.di

import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.UserRegDTOMapper
import com.decagonhq.clads.repository.AuthRepository
import com.decagonhq.clads.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        apiService: ApiService,
        userRegDTOMapper: UserRegDTOMapper
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, userRegDTOMapper)
    }
}
