package com.example.kmp_social_app.auth.data

import com.example.kmp_social_app.auth.data.dto.SingInRequestDTO
import com.example.kmp_social_app.auth.data.dto.SingUpRequestDTO
import com.example.kmp_social_app.auth.domain.AuthRepository
import com.example.kmp_social_app.auth.domain.model.AuthResult
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.NetworkResponse
import kotlinx.coroutines.withContext

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authService: AuthService
) : AuthRepository {

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): NetworkResponse<AuthResult> {
        return withContext(dispatcher.io) {
            try {
                val request = SingUpRequestDTO(
                    name = name,
                    email = email,
                    password = password
                )

                val response = authService.signUp(request)

                if (response.authData == null) {
                    NetworkResponse.Failure(message = response.errorMessage)
                } else {
                    NetworkResponse.Success(data = response.authData.toAuthResult())
                }
            } catch (ex: Exception) {
                NetworkResponse.Failure(message = ex.message)
            }
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): NetworkResponse<AuthResult> {
        return withContext(dispatcher.io) {
            try {
                val request = SingInRequestDTO(
                    email = email,
                    password = password
                )

                val response = authService.signIn(request)

                if (response.authData == null) {
                    NetworkResponse.Failure(message = response.errorMessage)
                } else {
                    NetworkResponse.Success(data = response.authData.toAuthResult())
                }
            } catch (ex: Exception) {
                NetworkResponse.Failure(message = ex.message)
            }
        }
    }
}






