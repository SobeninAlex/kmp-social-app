package com.example.kmp_social_app.common.utils

class UnauthorizedException: RuntimeException()

class SomethingWrongException(message: String?): RuntimeException(message)