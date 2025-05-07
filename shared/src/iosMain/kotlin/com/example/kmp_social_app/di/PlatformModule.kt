package com.example.kmp_social_app.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.kmp_social_app.common.data.IOSUserPreferences
import com.example.kmp_social_app.common.data.createDatastore
import com.example.kmp_social_app.common.data.local.UserPreferences
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<UserPreferences> {
        IOSUserPreferences(
            datastore = get()
        )
    }

    single<DataStore<Preferences>> { createDatastore()  }
}