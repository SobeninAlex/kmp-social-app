package com.example.kmp_social_app.common.utils

import android.util.Log

internal class AndroidHttpLog : HttpLog {

    override fun log(tag: String, message: String) {
        Log.d(tag, message)
        Log.d(tag, "=========================================================================")
    }
}