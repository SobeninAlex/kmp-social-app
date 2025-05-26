package ru.sobeninalex.account.domain.usecase

import ru.sobeninalex.account.domain.FeatureAccountRepository
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.utils.helpers.SomethingWrongException

internal class UpdateProfileUseCase(
    private val repository: FeatureAccountRepository
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