package ru.sobeninalex.account.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.sobeninalex.account.domain.FeatureAccountRepository
import ru.sobeninalex.common.models.profile.Profile

internal class GetProfileUseCase(
    private val repository: FeatureAccountRepository
) {

    operator fun invoke(profileId: String) : Flow<Profile> {
        return repository.getProfileById(profileId)
    }
}