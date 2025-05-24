package ru.sobeninalex.utils.preferences.user_prefs

interface UserPreferences {

    suspend fun getUserSettings(): UserSettings

    suspend fun setUserSettings(userSettings: UserSettings)

    suspend fun clearUserSetting()

    companion object {
        const val PREFERENCES_FILE_NAME = "app_user_settings.preferences_pb"
    }
}