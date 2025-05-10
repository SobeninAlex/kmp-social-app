package com.example.kmp_social_app.common.utils

import com.example.kmp_social_app.common.utils.Constants.BASE_URL

fun String.toCurrentUrl(): String {
    return "$BASE_URL${this.substring(21)}"
}