package ru.sobeninalex.authorization.presentation.signup

sealed interface SignUpAction {

    data class InputUsername(val username: String): SignUpAction

    data class InputEmail(val email: String): SignUpAction

    data class InputPassword(val password: String): SignUpAction

    data object OnSignUpClick : SignUpAction
}