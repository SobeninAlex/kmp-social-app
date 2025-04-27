package com.example.kmp_social_app.android.presentation.auth.signup

import com.example.kmp_social_app.android.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.InputEmail -> changeEmail(event.email)
            is SignUpEvent.InputPassword -> changePassword(event.password)
            is SignUpEvent.InputUsername -> changeUsername(event.username)
        }
    }

    private fun changeUsername(username: String) {
        _uiState.update { oldState ->
            oldState.copy(username = username)
        }
    }

    private fun changePassword(password: String) {
        _uiState.update { oldState ->
            oldState.copy(password = password)
        }
    }

    private fun changeEmail(email: String) {
        _uiState.update { oldState ->
            oldState.copy(email = email)
        }
    }
}