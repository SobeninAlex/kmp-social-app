package com.example.kmp_social_app.glue.data.datasource

import com.example.kmp_social_app.glue.mappers.toUserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sobeninalex.data.remote.services.authorization.AuthApiDataSource
import ru.sobeninalex.data.remote.services.authorization.AuthApiService
import ru.sobeninalex.data.remote.services.authorization.dto.AuthResultDTO
import ru.sobeninalex.data.remote.services.authorization.dto.SingInRequestDTO
import ru.sobeninalex.data.remote.services.authorization.dto.SingUpRequestDTO
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences

class AuthApiDataSourceImpl(
    private val userPreferences: UserPreferences,
    private val authApiService: AuthApiService,
) : AuthApiDataSource {

    override suspend fun signUp(name: String, email: String, password: String): AuthResultDTO {
        return withContext(Dispatchers.IO) {
            try {
                val request = SingUpRequestDTO(
                    name = name,
                    email = email,
                    password = password
                )

                val response = authApiService.signUp(request)

                response.authData?.also {
                    userPreferences.setUserSettings(it.toUserSettings())
                } ?: throw SomethingWrongException(message = response.errorMessage)
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResultDTO {
        return withContext(Dispatchers.IO) {
            try {
                val request = SingInRequestDTO(
                    email = email,
                    password = password
                )

                val response = authApiService.signIn(request)

                response.authData?.also {
                    userPreferences.setUserSettings(it.toUserSettings())
                } ?: throw SomethingWrongException(message = response.errorMessage)
            } catch (ex: Exception) {
                throw ex
            }
        }
    }
}