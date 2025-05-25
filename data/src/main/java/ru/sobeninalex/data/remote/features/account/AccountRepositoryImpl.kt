package ru.sobeninalex.data.remote.features.account

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sobeninalex.data.remote.features.account.dto.UpdateProfileRequestDTO
import ru.sobeninalex.domain.features.account.AccountRepository
import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.helpers.toServerUrl
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences

class AccountRepositoryImpl(
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

    override suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?): Profile {
        return withContext(Dispatchers.IO) {
            try {
                val userSetting = userPreferences.getUserSettings()

                val userData = Json.encodeToString(
                    serializer = UpdateProfileRequestDTO.serializer(),
                    value = UpdateProfileRequestDTO(
                        userId = profile.id,
                        name = profile.name,
                        bio = profile.bio,
                        avatar = profile.avatar?.toServerUrl()
                    )
                )

                val response = accountApiService.updateProfile(
                    token = userSetting.token,
                    userData = userData,
                    imageBytes = imageBytes
                )

                if (response.isSuccess) {
                    var avatar = profile.avatar
                    if (imageBytes != null) {
                        val profileResponse = accountApiService.getProfileById(
                            token = userSetting.token,
                            profileId = profile.id,
                            currentUserId = profile.id
                        )

                        profileResponse.user?.let {
                            avatar = it.avatar
                        }
                    }
                    val updateProfile = profile.copy(avatar = avatar)
                    userPreferences.setUserSettings(
                        updateProfile.toUserSettings(userSetting.token)
                    )
                    updateProfile
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw SomethingWrongException(message = ex.message)
            }
        }
    }
}