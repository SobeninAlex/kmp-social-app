package ru.sobeninalex.domain.features.authorization.usecase

import ru.sobeninalex.domain.features.authorization.AuthRepository
import ru.sobeninalex.domain.features.authorization.model.AuthResult
import ru.sobeninalex.utils.helpers.SomethingWrongException

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): AuthResult {
        if (email.isBlank() || "@" !in email) {
            throw SomethingWrongException(message = "Invalid email")
        }

        if (password.isBlank() || password.length < 4) {
            throw SomethingWrongException(message = "Invalid password")
        }

        return repository.signIn(
            email = email.trim(),
            password = password
        )
    }
}