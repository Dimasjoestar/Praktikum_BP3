package com.pab.moneytracker.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "MoneyTrackerPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USER_ID = "userId"
        private const val KEY_USER_EMAIL = "userEmail"
        private const val KEY_USER_FIRST_NAME = "userFirstName"
        private const val KEY_USER_LAST_NAME = "userLastName"
        private const val KEY_AUTH_PROVIDER = "authProvider"
        private const val KEY_DARK_MODE = "darkMode"
        private const val KEY_HIDE_NOMINAL = "hideNominal"
        private const val KEY_PROFILE_PHOTO = "profilePhoto"
    }

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var userId: Long
        get() = prefs.getLong(KEY_USER_ID, -1)
        set(value) = prefs.edit().putLong(KEY_USER_ID, value).apply()

    var userEmail: String
        get() = prefs.getString(KEY_USER_EMAIL, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_EMAIL, value).apply()

    var userFirstName: String
        get() = prefs.getString(KEY_USER_FIRST_NAME, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_FIRST_NAME, value).apply()

    var userLastName: String
        get() = prefs.getString(KEY_USER_LAST_NAME, "") ?: ""
        set(value) = prefs.edit().putString(KEY_USER_LAST_NAME, value).apply()

    var authProvider: String
        get() = prefs.getString(KEY_AUTH_PROVIDER, "EMAIL") ?: "EMAIL"
        set(value) = prefs.edit().putString(KEY_AUTH_PROVIDER, value).apply()

    // Fixed: Only save preference, don't apply theme here (prevents flickering)
    var isDarkMode: Boolean
        get() = prefs.getBoolean(KEY_DARK_MODE, false)
        set(value) = prefs.edit().putBoolean(KEY_DARK_MODE, value).apply()

    var isHideNominal: Boolean
        get() = prefs.getBoolean(KEY_HIDE_NOMINAL, false)
        set(value) = prefs.edit().putBoolean(KEY_HIDE_NOMINAL, value).apply()

    var profilePhotoUri: String?
        get() = prefs.getString(KEY_PROFILE_PHOTO, null)
        set(value) = prefs.edit().putString(KEY_PROFILE_PHOTO, value).apply()

    fun saveLoginSession(userId: Long, email: String, firstName: String, lastName: String, provider: String) {
        isLoggedIn = true
        this.userId = userId
        this.userEmail = email
        this.userFirstName = firstName
        this.userLastName = lastName
        this.authProvider = provider
    }

    fun clearSession() {
        // Preserve dark mode setting when clearing session
        val darkMode = isDarkMode
        prefs.edit().clear().apply()
        isDarkMode = darkMode
    }

    fun applyDarkMode(isDark: Boolean) {
        val nightMode = if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        if (AppCompatDelegate.getDefaultNightMode() != nightMode) {
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    fun initDarkMode() {
        applyDarkMode(isDarkMode)
    }

    // Toggle dark mode with apply
    fun toggleDarkMode(enabled: Boolean) {
        isDarkMode = enabled
        applyDarkMode(enabled)
    }
}
