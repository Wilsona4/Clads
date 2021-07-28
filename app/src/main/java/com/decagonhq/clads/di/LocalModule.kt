package com.decagonhq.clads.di

import android.content.Context
import androidx.room.Room
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.local.CladsDatabase.Companion.DATABASE_NAME
import com.decagonhq.clads.data.local.ClientDao
import com.decagonhq.clads.data.local.GalleryImageDao
import com.decagonhq.clads.data.local.ProfileImageDao
import com.decagonhq.clads.data.local.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesCladsDataBase(@ApplicationContext context: Context): CladsDatabase {
        return Room.databaseBuilder(context, CladsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesClientDAO(cladsDatabase: CladsDatabase): ClientDao {
        return cladsDatabase.clientDao()
    }

    @Singleton
    @Provides
    fun providesUserDAO(cladsDatabase: CladsDatabase): UserProfileDao {
        return cladsDatabase.userProfileDao()
    }

    @Singleton
    @Provides
    fun providesProfileImageDAO(cladsDatabase: CladsDatabase): ProfileImageDao {
        return cladsDatabase.profileImageDao()
    }

    @Singleton
    @Provides
    fun providesGalleryImageDAO(cladsDatabase: CladsDatabase): GalleryImageDao {
        return cladsDatabase.galleryImageDao()
    }
}
