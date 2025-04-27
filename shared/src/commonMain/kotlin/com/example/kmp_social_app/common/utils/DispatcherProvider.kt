package com.example.kmp_social_app.common.utils

import kotlinx.coroutines.CoroutineDispatcher

internal interface DispatcherProvider {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}

internal expect fun provideDispatcher(): DispatcherProvider