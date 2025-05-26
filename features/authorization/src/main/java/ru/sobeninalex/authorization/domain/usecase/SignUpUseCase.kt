package ru.sobeninalex.authorization.domain.usecase

import org.koin.core.component.KoinComponent
import ru.sobeninalex.authorization.domain.FeatureAuthorizationRepository
import ru.sobeninalex.common.models.auth.AuthResult
import ru.sobeninalex.utils.helpers.SomethingWrongException

internal class SignUpUseCase(
    private val repository: FeatureAuthorizationRepository
) : KoinComponent {

    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): AuthResult {
        if (name.isBlank() || name.length < 3) {
            throw SomethingWrongException(message = "Invalid name")
        }

        if (email.isBlank() || "@" !in email) {
            throw SomethingWrongException(message = "Invalid email")
        }

        if (password.isBlank() || password.length < 4) {
            throw SomethingWrongException(message = "Invalid password")
        }

        return repository.signUp(
            name = name.trim(),
            email = email.trim(),
            password = password
        )
    }
}