package com.example.kmp_social_app.feature.account.data

import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.utils.Constants
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.SomethingWrongException
import com.example.kmp_social_app.feature.account.data.dto.UpdateProfileRequestDTO
import com.example.kmp_social_app.feature.account.domain.AccountRepository
import com.example.kmp_social_app.feature.account.domain.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class AccountRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences,
    private val accountApiService: AccountApiService
) : AccountRepository {

    override fun getProfileById(profileId: String): Flow<Profile> {
        return flow {
            val userSettings = userPreferences.getUserSettings()

            if (profileId == userSettings.id) {
                emit(userSettings.toProfile())
            }

            val response = accountApiService.getProfileById(
                token = userSettings.token,
                profileId = profileId,
                currentUserId = userSettings.id
            )

            if (response.isSuccess) {
                response.user?.let {
                    emit(it.toProfile())
                } ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
            } else {
                throw SomethingWrongException(message = response.errorMessage)
            }
        }.catch { exception ->
            throw exception
        }.flowOn(dispatcher.io)
    }

    override suspend fun updateProfile(name: String, bio: String, imageBytes: ByteArray?): Profile {
        return withContext(dispatcher.io) {
            try {
                val userSetting = userPreferences.getUserSettings()

                val userData = Json.encodeToString(
                    serializer = UpdateProfileRequestDTO.serializer(),
                    value = UpdateProfileRequestDTO(
                        userId = userSetting.id,
                        name = name,
                        bio = bio,
                    )
                )

                val response = accountApiService.updateProfile(
                    token = userSetting.token,
                    userData = userData,
                    imageBytes = imageBytes
                )

                if (response.isSuccess) {
                    val profileResponse = accountApiService.getProfileById(
                        token = userSetting.token,
                        profileId = userSetting.id,
                        currentUserId = userSetting.id
                    )

                    if (profileResponse.isSuccess) {
                        profileResponse.user?.let {
                            userPreferences.setUserSettings(it.toProfile().toUserSettings(userSetting.token))
                            it.toProfile()
                        } ?: throw SomethingWrongException(message = profileResponse.errorMessage)
                    } else {
                        throw SomethingWrongException(message = profileResponse.errorMessage)
                    }
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw SomethingWrongException(message = ex.message)
            }
        }
    }
}