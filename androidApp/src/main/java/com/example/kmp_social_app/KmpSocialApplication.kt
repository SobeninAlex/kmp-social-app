package com.example.kmp_social_app

import android.app.Application
import com.example.kmp_social_app.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.sobeninalex.account.di.AccountFeatureModule
import ru.sobeninalex.authorization.di.AuthorizationFeatureModule
import ru.sobeninalex.data.di.getDataModules
import ru.sobeninalex.domain.di.getDomainModules
import ru.sobeninalex.home.di.HomeFeatureModule
import ru.sobeninalex.post_detail.di.PostDetailFeatureModule
import ru.sobeninalex.utils.di.UtilsModule

class KmpSocialApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KmpSocialApplication)
            modules(
                getDataModules() + getDomainModules() + listOf(
                    AppModule,
                    UtilsModule,
                    AuthorizationFeatureModule,
                    AccountFeatureModule,
                    HomeFeatureModule,
                    PostDetailFeatureModule,
                )
            )
        }
    }
}