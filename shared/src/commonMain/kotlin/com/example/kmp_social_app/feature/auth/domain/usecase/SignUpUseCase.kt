package com.example.kmp_social_app.feature.auth.domain.usecase

import com.example.kmp_social_app.common.utils.SomethingWrongException
import com.example.kmp_social_app.feature.auth.domain.AuthRepository
import com.example.kmp_social_app.feature.auth.domain.model.AuthResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpUseCase : KoinComponent {

    private val repository by inject<AuthRepository>()

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