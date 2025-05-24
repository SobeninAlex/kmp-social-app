package ru.sobeninalex.domain.features.authorization.usecase

import org.koin.core.component.KoinComponent
import ru.sobeninalex.domain.features.authorization.AuthRepository
import ru.sobeninalex.domain.features.authorization.model.AuthResult
import ru.sobeninalex.utils.helpers.SomethingWrongException

class SignUpUseCase(
    private val repository: AuthRepository
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