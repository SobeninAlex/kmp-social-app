package ru.sobeninalex.authorization.domain.usecase

import ru.sobeninalex.authorization.domain.FeatureAuthorizationRepository
import ru.sobeninalex.common.models.auth.AuthResult
import ru.sobeninalex.utils.helpers.SomethingWrongException

internal class LoginUseCase(
    private val repository: FeatureAuthorizationRepository
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