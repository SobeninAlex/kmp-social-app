package com.example.kmp_social_app.common.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.kmp_social_app.common.data.local.PREFERENCES_FILE_NAME
import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.data.local.UserSettings
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal class IOSUserPreferences(
    private val datastore: DataStore<Preferences>
) : UserPreferences {

    override suspend fun getUserSettings(): UserSettings {
        TODO("Not yet implemented")
    }

    override suspend fun setUserSettings(userSettings: UserSettings) {
        TODO("Not yet implemented")
    }

    override suspend fun clearUserSetting() {
        TODO("Not yet implemented")
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun createDatastore(): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        produceFile = {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            (requireNotNull(documentDirectory).path + "/$PREFERENCES_FILE_NAME").toPath()
        }
    )
}