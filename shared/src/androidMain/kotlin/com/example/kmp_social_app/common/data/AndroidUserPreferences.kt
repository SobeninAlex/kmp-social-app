package com.example.kmp_social_app.common.data

import androidx.datastore.core.DataStore
import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.data.local.UserSettings
import kotlinx.coroutines.flow.first

internal class AndroidUserPreferences(
    private val datastore: DataStore<UserSettings>
) : UserPreferences {

    override suspend fun getUserSettings(): UserSettings {
        return datastore.data.first()
    }

    override suspend fun setUserSettings(userSettings: UserSettings) {
        datastore.updateData { userSettings }
    }

    override suspend fun clearUserSetting() {
        datastore.updateData { UserSettings() }
    }
}