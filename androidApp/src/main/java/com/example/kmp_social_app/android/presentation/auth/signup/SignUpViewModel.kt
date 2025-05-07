package com.example.kmp_social_app.android.presentation.auth.signup

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.utils.BaseViewModel
import com.example.kmp_social_app.common.utils.NetworkResponse
import com.example.kmp_social_app.feature.auth.domain.usecase.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.InputEmail -> changeEmail(event.email)
            is SignUpEvent.InputPassword -> changePassword(event.password)
            is SignUpEvent.InputUsername -> changeUsername(event.username)
            is SignUpEvent.OnSignUpClick -> signUp()
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                signUpUseCase(
                    name = uiState.value.username,
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
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isAuthSuccess = true
                            )
                        }
                    }
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(isLoading = false)
                }
                showSnackbar(message = error.message)
            }
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