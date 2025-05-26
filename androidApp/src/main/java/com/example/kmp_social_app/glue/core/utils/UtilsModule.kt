package com.example.kmp_social_app.glue.core.utils

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.sobeninalex.utils.helpers.ImageByteReader
import ru.sobeninalex.utils.preferences.AppPreferences
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings
import ru.sobeninalex.utils.preferences.user_prefs.UserSettingsSerializer

val UtilsModule = module {
    single<UserPreferences> {
        AppPreferences(
            userDatastore = get()
        )
    }

    single<DataStore<UserSettings>> {
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                androidContext().dataStoreFile(
                    fileName = UserPreferences.PREFERENCES_FILE_NAME
                )
            }
        )
    }

    single<ImageByteReader> {
        ImageByteReader(
            context = androidContext()
        )
    }
}