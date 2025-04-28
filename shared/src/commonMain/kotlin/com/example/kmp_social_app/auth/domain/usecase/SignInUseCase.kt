package com.example.kmp_social_app.auth.domain.usecase

import com.example.kmp_social_app.auth.domain.AuthRepository
import com.example.kmp_social_app.auth.domain.model.AuthResult
import com.example.kmp_social_app.common.utils.NetworkResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInUseCase: KoinComponent {

    private val repository by inject<AuthRepository>()

    suspend operator fun invoke(
        email: String,
        password: String
    ): NetworkResponse<AuthResult> {
        if (email.isBlank() || "@" !in email) {
            return NetworkResponse.Failure(message = "Invalid email")
        }

        if (password.isBlank() || password.length < 4) {
            return NetworkResponse.Failure(message = "Invalid password")
        }

        return repository.signIn(
            email = email.trim(),
            password = password
        )
    }
}