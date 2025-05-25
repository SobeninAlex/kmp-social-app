package ru.sobeninalex.domain.features.account.usecase

import ru.sobeninalex.domain.features.account.AccountRepository
import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.utils.helpers.SomethingWrongException

class UpdateProfileUseCase(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(
        profile: Profile,
        imageBytes: ByteArray?
    ): Profile {
        if (profile.name.isBlank() || profile.name.length < 3 || profile.name.length > 50) {
            throw SomethingWrongException(message = "Invalid name")
        }

        if (profile.bio.isBlank() || profile.bio.length > 150) {
            throw SomethingWrongException(message = "Invalid bio")
        }

        return repository.updateProfile(
            imageBytes = imageBytes,
            profile = profile
        )
    }
}