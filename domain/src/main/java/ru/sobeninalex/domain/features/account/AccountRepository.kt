package ru.sobeninalex.domain.features.account

import kotlinx.coroutines.flow.Flow
import ru.sobeninalex.domain.features.account.model.Profile

interface AccountRepository {

    fun getProfileById(profileId: String): Flow<Profile>

    suspend fun updateProfile(name: String, bio: String, imageBytes: ByteArray?): Profile
}