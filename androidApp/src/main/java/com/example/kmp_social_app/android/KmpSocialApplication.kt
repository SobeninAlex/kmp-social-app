package com.example.kmp_social_app.android

import android.app.Application
import com.example.kmp_social_app.android.di.appModule
import com.example.kmp_social_app.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KmpSocialApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KmpSocialApplication)
            modules(appModule + getSharedModules())
        }
    }
}