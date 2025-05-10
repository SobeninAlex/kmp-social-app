package com.example.kmp_social_app.android

import android.app.Application
import com.example.kmp_social_app.android.di.appModule
import com.example.kmp_social_app.android.common.utils.Core
import com.example.kmp_social_app.android.common.utils.CoreProvider
import com.example.kmp_social_app.di.getSharedModules
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KmpSocialApplication : Application() {

    private val coreProvider: CoreProvider by inject<CoreProvider>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KmpSocialApplication)
            modules(appModule + getSharedModules())
        }

        Core.init(coreProvider)
    }
}