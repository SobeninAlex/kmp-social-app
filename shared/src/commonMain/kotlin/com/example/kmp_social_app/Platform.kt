package com.example.kmp_social_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform