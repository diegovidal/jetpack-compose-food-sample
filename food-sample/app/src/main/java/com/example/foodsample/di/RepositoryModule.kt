package com.example.foodsample.di

import com.example.foodsample.repository.RecipeRepository
import com.example.foodsample.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideRepository(recipeRepositoryImpl: RecipeRepositoryImpl): RecipeRepository

}