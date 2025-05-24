package ru.sobeninalex.authorization.presentation.signup

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.domain.features.authorization.usecase.SignUpUseCase
import ru.sobeninalex.utils.presentation.BaseViewModel

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.InputEmail -> changeEmail(action.email)
            is SignUpAction.InputPassword -> changePassword(action.password)
            is SignUpAction.InputUsername -> changeUsername(action.username)
            is SignUpAction.OnSignUpClick -> signUp()
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
            }.onSuccess { _ ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isAuthSuccess = true
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(isLoading = false)
                }
                throw error
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