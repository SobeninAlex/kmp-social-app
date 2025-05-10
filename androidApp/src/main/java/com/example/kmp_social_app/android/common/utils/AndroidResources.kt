package com.example.kmp_social_app.android.common.utils

import android.content.Context

class AndroidResources(
    private val appContext: Context
) {

    fun getString(id: Int): String {
        return appContext.getString(id)
    }
}