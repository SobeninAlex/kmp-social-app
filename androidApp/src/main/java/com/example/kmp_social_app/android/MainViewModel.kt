package com.example.kmp_social_app.android

import androidx.datastore.core.DataStore
import com.example.kmp_social_app.android.common.datastore.UserSettings
import com.example.kmp_social_app.android.utils.BaseViewModel
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val dataStore: DataStore<UserSettings>
) : BaseViewModel() {

    val authState = dataStore.data.map { it.token }
}