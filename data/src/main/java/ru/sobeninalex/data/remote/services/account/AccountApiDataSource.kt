package ru.sobeninalex.data.remote.services.account

import kotlinx.coroutines.flow.Flow
import ru.sobeninalex.data.remote.services.account.dto.ProfileDTO

interface AccountApiDataSource {

    fun getProfileById(profileId: String): Flow<ProfileDTO>

    suspend fun updateProfile(profile: ProfileDTO, imageBytes: ByteArray?): ProfileDTO
}