package ru.sobeninalex.data.remote.features.authorization

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.data.remote.features.authorization.dto.SingInRequestDTO
import ru.sobeninalex.data.remote.features.authorization.dto.SingUpRequestDTO
import ru.sobeninalex.domain.features.authorization.AuthRepository
import ru.sobeninalex.domain.features.authorization.model.AuthResult
import ru.sobeninalex.utils.helpers.SomethingWrongException

internal class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferences,
) : AuthRepository {

    override suspend fun signUp(
        name: String,
        email: String,
        password: String
    ): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val request = SingUpRequestDTO(
                    name = name,
                    email = email,
                    password = password
                )

                val response = authApiService.signUp(request)

                if (response.errorMessage != null) {
                    throw SomethingWrongException(message = response.errorMessage)
                } else if (response.authData == null) {
                    throw SomethingWrongException(message = response.errorMessage)
                } else {
                    response.authData.toAuthResult().also {
                        userPreferences.setUserSettings(it.toUserSettings())
                    }
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val request = SingInRequestDTO(
                    email = email,
                    password = password
                )

                val response = authApiService.signIn(request)

                if (response.authData == null) {
                    throw SomethingWrongException(message = response.errorMessage)
                } else {
                    response.authData.toAuthResult().also {
                        userPreferences.setUserSettings(it.toUserSettings())
                    }
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }
}






