package ru.sobeninalex.account.presentation.edit

import androidx.compose.runtime.Immutable
import ru.sobeninalex.domain.features.account.model.Profile

@Immutable
data class EditProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile = Profile.Default,
    val updateSucceed: Boolean = false,
) {

    companion object {
        val Preview = EditProfileUiState(
            profile = Profile.Preview,
            updateSucceed = false
        )
    }
}