package com.example.foodsample

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    // Should be saved in datastore or cache
    val isDark = mutableStateOf(false)

    fun toggleTheme() {

        isDark.value = isDark.value.not()
    }
}