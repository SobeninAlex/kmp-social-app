package ru.sobeninalex.account.presentation.edit

import androidx.compose.runtime.Immutable
import ru.sobeninalex.common.models.profile.Profile

@Immutable
internal data class EditProfileUiState(
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