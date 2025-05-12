package com.example.kmp_social_app.feature.account.data

import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.utils.Constants
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.SomethingWrongException
import com.example.kmp_social_app.feature.account.domain.AccountRepository
import com.example.kmp_social_app.feature.account.domain.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
}