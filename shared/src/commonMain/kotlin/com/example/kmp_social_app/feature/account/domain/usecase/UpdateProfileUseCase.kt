package com.example.kmp_social_app.feature.account.domain.usecase

import com.example.kmp_social_app.common.utils.SomethingWrongException
import com.example.kmp_social_app.feature.account.domain.AccountRepository
import com.example.kmp_social_app.feature.account.domain.model.Profile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateProfileUseCase : KoinComponent {

    private val repository by inject<AccountRepository>()

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