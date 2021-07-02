package com.decagonhq.clads.di

import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.local.UserProfileEntityMapper
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.ImageDTOMapper
import com.decagonhq.clads.data.remote.login.LoginCredentialsDTOMapper
import com.decagonhq.clads.data.remote.profile.UserProfileDTOMapper
import com.decagonhq.clads.data.remote.registration.UserRegDTOMapper
import com.decagonhq.clads.repository.AuthRepository
import com.decagonhq.clads.repository.AuthRepositoryImpl
import com.decagonhq.clads.repository.ImageRepository
import com.decagonhq.clads.repository.ImageRepositoryImpl
import com.decagonhq.clads.repository.UserProfileRepository
import com.decagonhq.clads.repository.UserProfileRepositoryImpl
import com.decagonhq.clads.util.Constants.IMAGE_API_SERVICE
import com.decagonhq.clads.util.Constants.MAIN_API_SERVICE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        @Named(MAIN_API_SERVICE) apiService: ApiService,
        userRegDTOMapper: UserRegDTOMapper,
        loginCredentialsDTOMapper: LoginCredentialsDTOMapper,
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, userRegDTOMapper, loginCredentialsDTOMapper)
    }

    @Singleton
    @Provides
    fun providesUserProfileRepository(
        @Named(MAIN_API_SERVICE) apiService: ApiService,
        userProfileDTOMapper: UserProfileDTOMapper,
        userProfileEntityMapper: UserProfileEntityMapper,
        database: CladsDatabase
    ): UserProfileRepository {
        return UserProfileRepositoryImpl(
            apiService,
            userProfileDTOMapper,
            userProfileEntityMapper,
            database
        )
    }

    @Singleton
    @Provides
    fun provideImageRepository(
        @Named(IMAGE_API_SERVICE) apiService: ApiService,
        imageDTOMapper: ImageDTOMapper
    ): ImageRepository {
        return ImageRepositoryImpl(apiService, imageDTOMapper)
    }
}
