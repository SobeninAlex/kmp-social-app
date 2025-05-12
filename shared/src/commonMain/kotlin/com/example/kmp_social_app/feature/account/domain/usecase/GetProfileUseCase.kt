package com.example.kmp_social_app.feature.account.domain.usecase

import com.example.kmp_social_app.feature.account.domain.AccountRepository
import com.example.kmp_social_app.feature.account.domain.model.Profile
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProfileUseCase : KoinComponent {

    private val repository by inject<AccountRepository>()

    operator fun invoke(profileId: String) : Flow<Profile> {
        return repository.getProfileById(profileId)
    }
}