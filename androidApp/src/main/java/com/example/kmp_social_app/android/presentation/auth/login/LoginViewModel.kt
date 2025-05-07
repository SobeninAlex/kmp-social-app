package com.example.kmp_social_app.android.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.utils.BaseViewModel
import com.example.kmp_social_app.common.utils.NetworkResponse
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

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.InputEmail -> changeEmail(event.email)
            is LoginEvent.InputPassword -> changePassword(event.password)
            is LoginEvent.OnLoginClick -> login()
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
            }.onSuccess { response ->
                when (response) {
                    is NetworkResponse.Failure -> {
                        _uiState.update { it.copy(isLoading = false) }
                        showSnackbar(message = response.message)
                    }

                    is NetworkResponse.Success -> {
                        _uiState.update { it.copy(isLoading = false, isLoginSuccess = true) }
                    }
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false) }
                showSnackbar(message = error.message)
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