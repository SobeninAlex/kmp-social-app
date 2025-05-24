package com.example.kmp_social_app

import android.app.Application
import com.example.kmp_social_app.di.AppModule
import com.example.kmp_social_app.di.CoreModules
import com.example.kmp_social_app.di.DataModules
import com.example.kmp_social_app.di.DomainModules
import com.example.kmp_social_app.di.FeatureModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KmpSocialApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KmpSocialApplication)
            modules(
                AppModule
                        + CoreModules
                        + DataModules
                        + DomainModules
                        + FeatureModules
            )
        }
    }
}