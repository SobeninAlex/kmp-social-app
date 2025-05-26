package ru.sobeninalex.authorization.presentation.login

internal sealed interface LoginAction {

    data class InputEmail(val email: String): LoginAction

    data class InputPassword(val password: String): LoginAction

    data object OnLoginClick : LoginAction
}