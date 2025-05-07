package com.example.kmp_social_app.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.kmp_social_app.common.data.AndroidUserPreferences
import com.example.kmp_social_app.common.data.UserSettingsSerializer
import com.example.kmp_social_app.common.data.local.PREFERENCES_FILE_NAME
import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.data.local.UserSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<UserPreferences> {
        AndroidUserPreferences(
            datastore = get()
        )
    }

    single<DataStore<UserSettings>> {
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                androidContext().dataStoreFile(
                    fileName = PREFERENCES_FILE_NAME
                )
            }
        )
    }
}