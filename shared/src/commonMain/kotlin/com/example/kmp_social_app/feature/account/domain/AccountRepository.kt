package com.example.kmp_social_app.feature.account.domain

import com.example.kmp_social_app.feature.account.domain.model.Profile
import kotlinx.coroutines.flow.Flow

internal interface AccountRepository {

    fun getProfileById(profileId: String): Flow<Profile>

    suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?): Profile
}