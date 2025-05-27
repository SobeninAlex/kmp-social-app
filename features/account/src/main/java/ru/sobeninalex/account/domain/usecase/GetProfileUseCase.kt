package ru.sobeninalex.account.domain.usecase

import ru.sobeninalex.account.domain.FeatureAccountRepository

internal class GetProfileUseCase(
    private val repository: FeatureAccountRepository
) {

    operator fun invoke(profileId: String)= repository.getProfileById(profileId)
}