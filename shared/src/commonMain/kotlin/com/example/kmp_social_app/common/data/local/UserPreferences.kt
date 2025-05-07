package com.example.kmp_social_app.common.data.local

internal const val PREFERENCES_FILE_NAME = "app_user_settings.preferences_pb"

internal interface UserPreferences {

    suspend fun getUserSettings(): UserSettings

    suspend fun setUserSettings(userSettings: UserSettings)
}