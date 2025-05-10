package com.example.kmp_social_app.android.common.utils

object Core {

    private lateinit var coreProvider: CoreProvider

    val resources: AndroidResources get() = coreProvider.resources

    fun init(coreProvider: CoreProvider) {
        Core.coreProvider = coreProvider
    }
}