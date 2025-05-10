package com.example.kmp_social_app.android.presentation.auth.login

sealed interface LoginAction {

    data class InputEmail(val email: String): LoginAction

    data class InputPassword(val password: String): LoginAction

    data object OnLoginClick : LoginAction
}