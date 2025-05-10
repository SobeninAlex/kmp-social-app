package com.example.kmp_social_app.android.presentation.auth.login

import com.example.kmp_social_app.android.common.utils.BaseViewModel
import com.example.kmp_social_app.feature.auth.domain.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.InputEmail -> changeEmail(action.email)
            is LoginAction.InputPassword -> changePassword(action.password)
            is LoginAction.OnLoginClick -> login()
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                signInUseCase(
                    email = uiState.value.email,
                    password = uiState.value.password
                )
            }.onSuccess { _ ->
                _uiState.update { it.copy(isLoading = false, isLoginSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false) }
                throw error
            }
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