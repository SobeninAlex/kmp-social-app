package com.example.kmp_social_app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.sobeninalex.authorization.di.AuthorizationFeatureModule
import ru.sobeninalex.data.di.getDataModules
import ru.sobeninalex.domain.di.getDomainModules
import ru.sobeninalex.utils.di.UtilsModule

class KmpSocialApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KmpSocialApplication)
            modules(
                listOf(
                    UtilsModule,
                    AuthorizationFeatureModule,

                )
                        + getDataModules()
                        + getDomainModules()
            )
        }
    }
}