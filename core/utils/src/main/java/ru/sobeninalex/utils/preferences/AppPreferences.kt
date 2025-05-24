package ru.sobeninalex.utils.preferences

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

class AppPreferences(
    private val userDatastore: DataStore<UserSettings>
) : UserPreferences {

    override suspend fun getUserSettings(): UserSettings {
        return userDatastore.data.first()
    }

    override suspend fun setUserSettings(userSettings: UserSettings) {
        userDatastore.updateData { userSettings }
    }

    override suspend fun clearUserSetting() {
        userDatastore.updateData { UserSettings() }
    }
}