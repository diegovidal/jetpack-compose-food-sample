package com.example.foodsample.di

import android.content.Context
import com.example.foodsample.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(
        @ApplicationContext app: Context
    ) = app as BaseApplication

    @Singleton
    @Provides
    @Named("random_str")
    fun provideRandomString(): String = "Hey this is a random string!"

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
}