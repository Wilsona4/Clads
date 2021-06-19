package com.decagonhq.clads.di

import android.content.Context
import android.content.SharedPreferences
import com.decagonhq.clads.util.Constants.SHARED_PREFS
import com.decagonhq.clads.util.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /*Provides sharedPreference where needed in the application*/
    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            SHARED_PREFS,
            Context.MODE_PRIVATE
        )
    }

    /*Provides Session Manager*/
    @Singleton
    @Provides
    fun providesSessionManager(
        sharedPreferences: SharedPreferences
    ): SessionManager {
        return SessionManager(sharedPreferences)
    }
}
