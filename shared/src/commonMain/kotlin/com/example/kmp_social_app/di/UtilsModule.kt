package com.example.kmp_social_app.di

import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.provideDispatcher
import org.koin.dsl.module

internal val utilsModule = module {
    factory<DispatcherProvider> { provideDispatcher() }
}