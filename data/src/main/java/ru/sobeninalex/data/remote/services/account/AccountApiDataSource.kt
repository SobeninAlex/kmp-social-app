package ru.sobeninalex.data.remote.services.account

import kotlinx.coroutines.flow.StateFlow
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.data.remote.services.account.dto.ProfileDTO

interface AccountApiDataSource {

    fun getProfileById(profileId: String): StateFlow<Profile>

    suspend fun updateProfile(profile: ProfileDTO, imageBytes: ByteArray?): ProfileDTO
}