package ru.sobeninalex.domain.features.account.usecase

import kotlinx.coroutines.flow.Flow
import ru.sobeninalex.domain.features.account.AccountRepository
import ru.sobeninalex.domain.features.account.model.Profile

class GetProfileUseCase(
    private val repository: AccountRepository
) {

    operator fun invoke(profileId: String) : Flow<Profile> {
        return repository.getProfileById(profileId)
    }
}