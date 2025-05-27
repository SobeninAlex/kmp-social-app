package com.example.kmp_social_app

import android.app.Application
import com.example.kmp_social_app.di.AppModule
import com.example.kmp_social_app.glue.core.utils.UtilsModule
import com.example.kmp_social_app.glue.data.DataModule
import ru.sobeninalex.data.remote.di.ApiServiceModule
import com.example.kmp_social_app.glue.features.account.FeatureAccountModule
import com.example.kmp_social_app.glue.features.authorization.FeatureAuthorizationModule
import com.example.kmp_social_app.glue.features.home.FeatureHomeModule
import com.example.kmp_social_app.glue.features.post_detail.FeaturePostDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KmpSocialApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KmpSocialApplication)
            modules(
                UtilsModule,
                AppModule,
                DataModule,

                FeatureAccountModule,
                FeatureAuthorizationModule,
                FeatureHomeModule,
                FeaturePostDetailModule,
            )
        }
    }
}