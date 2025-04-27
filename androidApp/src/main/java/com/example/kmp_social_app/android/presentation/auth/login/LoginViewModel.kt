package com.example.kmp_social_app.android.presentation.auth.login

import androidx.lifecycle.ViewModel
import com.example.kmp_social_app.android.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.InputEmail -> changeEmail(event.email)
            is LoginEvent.InputPassword -> changePassword(event.password)
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