package ru.sobeninalex.data.remote.features.account

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.data.remote.features.account.dto.UpdateProfileRequestDTO
import ru.sobeninalex.domain.features.account.AccountRepository
import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException

internal class AccountRepositoryImpl(
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
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateProfile(name: String, bio: String, imageBytes: ByteArray?): Profile {
        return withContext(Dispatchers.IO) {
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
                            userPreferences.setUserSettings(
                                it.toProfile().toUserSettings(userSetting.token))
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