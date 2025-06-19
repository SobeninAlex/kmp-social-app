package com.example.kmp_social_app

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import com.example.kmp_social_app.di.AppModule
import com.example.kmp_social_app.glue.core.common.CommonModule
import com.example.kmp_social_app.glue.core.utils.UtilsModule
import com.example.kmp_social_app.glue.data.DataModule
import com.example.kmp_social_app.glue.features.account.FeatureAccountModule
import com.example.kmp_social_app.glue.features.authorization.FeatureAuthorizationModule
import com.example.kmp_social_app.glue.features.home.FeatureHomeModule
import com.example.kmp_social_app.glue.features.post_detail.FeaturePostDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KmpSocialApplication : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KmpSocialApplication)
            modules(
                UtilsModule,
                CommonModule,

                AppModule,

                DataModule,

                FeatureAccountModule,
                FeatureAuthorizationModule,
                FeatureHomeModule,
                FeaturePostDetailModule,
            )
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizeBytes(100L * 1024 * 1024)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}