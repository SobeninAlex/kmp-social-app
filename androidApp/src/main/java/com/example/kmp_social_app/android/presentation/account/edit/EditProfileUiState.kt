package com.example.kmp_social_app.android.presentation.account.edit

import androidx.compose.runtime.Immutable
import com.example.kmp_social_app.feature.profile.domain.model.Profile

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