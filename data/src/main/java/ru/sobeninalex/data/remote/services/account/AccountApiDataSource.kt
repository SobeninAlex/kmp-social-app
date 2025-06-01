package ru.sobeninalex.data.remote.services.account

import kotlinx.coroutines.flow.StateFlow
import ru.sobeninalex.common.models.profile.Profile

interface AccountApiDataSource {

    fun getProfileById(profileId: String): StateFlow<Profile>

    suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?): Profile
}