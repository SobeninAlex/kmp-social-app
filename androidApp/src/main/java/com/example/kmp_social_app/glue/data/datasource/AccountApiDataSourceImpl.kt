package com.example.kmp_social_app.glue.data.datasource

import com.example.kmp_social_app.glue.mappers.toProfile
import com.example.kmp_social_app.glue.mappers.toUserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.common.presentation.CustomCoroutineScope
import ru.sobeninalex.data.remote.services.account.AccountApiDataSource
import ru.sobeninalex.data.remote.services.account.AccountApiService
import ru.sobeninalex.data.remote.services.account.dto.UpdateProfileRequestDTO
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.helpers.mergeWith
import ru.sobeninalex.utils.helpers.toClientUrl
import ru.sobeninalex.utils.helpers.toServerUrl
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences

class AccountApiDataSourceImpl(
    private val userPreferences: UserPreferences,
    private val accountApiService: AccountApiService,
    private val coroutineScope: CustomCoroutineScope,
) : AccountApiDataSource {
    private val refreshProfileFlow = MutableSharedFlow<Profile>()
    private var _profileId: String = ""

    private val profile = flow {
        val userSettings = userPreferences.getUserSettings()

        if (_profileId == userSettings.id) {
            emit(userSettings.toProfile())
        }

        val response = accountApiService.getProfileById(
            token = userSettings.token,
            profileId = _profileId,
            currentUserId = userSettings.id
        )

        if (response.isSuccess) {
            response.user?.let {
                emit(it.toProfile())
            } ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
        } else {
            throw SomethingWrongException(message = response.errorMessage)
        }
    }


    override fun getProfileById(profileId: String): StateFlow<Profile> {
        _profileId = profileId
        return profile.mergeWith(refreshProfileFlow)
            .catch { exception ->
                throw exception
            }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.Lazily,
                initialValue = Profile.Preview
            )
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
                            avatar = it.avatar?.toClientUrl()
                        }
                    }
                    val updateProfile = profile.copy(avatar = avatar)
                    userPreferences.setUserSettings(
                        updateProfile.toUserSettings(userSetting.token)
                    )
                    refreshProfileFlow.emit(updateProfile)
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