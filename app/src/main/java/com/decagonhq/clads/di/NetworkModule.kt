package com.decagonhq.clads.di

import android.content.SharedPreferences
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.util.Constants.BASE_URL
import com.decagonhq.clads.util.Constants.IMAGE_API_SERVICE
import com.decagonhq.clads.util.Constants.IMAGE_BASE_URL
import com.decagonhq.clads.util.Constants.IMAGE_RETROFIT
import com.decagonhq.clads.util.Constants.MAIN_API_SERVICE
import com.decagonhq.clads.util.Constants.MAIN_RETROFIT
import com.decagonhq.clads.util.Constants.TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /*Add authorization token to the header interceptor*/
    @Provides
    @Singleton
    fun provideHeaderInterceptor(sharedPreferences: SharedPreferences): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
            sharedPreferences.getString(TOKEN, null)?.let {
                request.addHeader("Authorization", "Bearer $it")
            }
            chain.proceed(request.build())
        }
    }

    @Provides
    @Singleton
    fun provideClient(
        headerAuthorization: Interceptor,
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .addInterceptor(headerAuthorization)
            .addInterceptor(logger)
//            .connectTimeout(timeOutSec, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Named(MAIN_RETROFIT)
    @Provides
    @Singleton
    fun provideRetrofitService(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Named(MAIN_API_SERVICE)
    @Provides
    @Singleton
    fun provideApiService(@Named(MAIN_RETROFIT) retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Named(IMAGE_RETROFIT)
    @Provides
    @Singleton
    fun provideImageRetrofitService(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(IMAGE_BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Named(IMAGE_API_SERVICE)
    @Provides
    @Singleton
    fun provideImageApiService(@Named(IMAGE_RETROFIT) retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
