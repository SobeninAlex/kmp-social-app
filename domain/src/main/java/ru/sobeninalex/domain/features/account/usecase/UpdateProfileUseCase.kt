package ru.sobeninalex.domain.features.account.usecase

import ru.sobeninalex.domain.features.account.AccountRepository
import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.utils.helpers.SomethingWrongException

class UpdateProfileUseCase(
    private val repository: AccountRepository
) {

    suspend operator fun invoke(
        name: String,
        bio: String,
        imageBytes: ByteArray?
    ): Profile {
        if (name.isBlank() || name.length < 3 || name.length > 50) {
            throw SomethingWrongException(message = "Invalid name")
        }

        if (bio.isBlank() || bio.length > 150) {
            throw SomethingWrongException(message = "Invalid bio")
        }

        return repository.updateProfile(
            imageBytes = imageBytes,
            name = name,
            bio = bio
        )
    }
}