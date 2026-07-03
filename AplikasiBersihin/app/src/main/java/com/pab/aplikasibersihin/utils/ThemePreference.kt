package com.pab.aplikasibersihin.utils

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class ThemeMode {
    LIGHT, DARK;

    val displayName: String
        get() = when (this) {
            LIGHT -> "Light Mode"
            DARK -> "Dark Mode"
        }
}

class ThemePreference private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(getSavedThemeMode())
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private fun getSavedThemeMode(): ThemeMode {
        val value = prefs.getString(KEY_THEME_MODE, ThemeMode.LIGHT.name) ?: ThemeMode.LIGHT.name
        return try {
            ThemeMode.valueOf(value)
        } catch (e: Exception) {
            ThemeMode.LIGHT
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
        _themeMode.value = mode
    }

    fun toggleTheme() {
        val newMode = if (_themeMode.value == ThemeMode.LIGHT) ThemeMode.DARK else ThemeMode.LIGHT
        setThemeMode(newMode)
    }

    companion object {
        private const val PREFS_NAME = "bersihin_theme_prefs"
        private const val KEY_THEME_MODE = "theme_mode"

        @Volatile
        private var INSTANCE: ThemePreference? = null

        fun getInstance(context: Context): ThemePreference {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ThemePreference(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
