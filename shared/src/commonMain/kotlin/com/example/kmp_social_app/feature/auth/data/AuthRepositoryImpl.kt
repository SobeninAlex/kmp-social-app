package com.example.kmp_social_app.feature.auth.data

import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.data.local.toUserSettings
import com.example.kmp_social_app.feature.auth.data.dto.SingInRequestDTO
import com.example.kmp_social_app.feature.auth.data.dto.SingUpRequestDTO
import com.example.kmp_social_app.feature.auth.domain.AuthRepository
import com.example.kmp_social_app.feature.auth.domain.model.AuthResult
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.NetworkResponse
import kotlinx.coroutines.withContext

internal class AuthRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferences,
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

                val response = authApiService.signUp(request)

                if (response.errorMessage != null) {
                    NetworkResponse.Failure(message = response.errorMessage)
                } else if (response.authData == null) {
                    NetworkResponse.Failure(message = response.errorMessage)
                } else {
                    userPreferences.setUserSettings(
                        response.authData.toAuthResult().toUserSettings()
                    )
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

                val response = authApiService.signIn(request)

                if (response.authData == null) {
                    NetworkResponse.Failure(message = response.errorMessage)
                } else {
                    userPreferences.setUserSettings(
                        response.authData.toAuthResult().toUserSettings()
                    )
                    NetworkResponse.Success(data = response.authData.toAuthResult())
                }
            } catch (ex: Exception) {
                NetworkResponse.Failure(message = ex.message)
            }
        }
    }
}






