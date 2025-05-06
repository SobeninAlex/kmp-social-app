package com.example.kmp_social_app.android.presentation.account.edit

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.utils.BaseViewModel
import com.example.kmp_social_app.feature.profile.domain.model.Profile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val args: EditProfileArgs
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { oldState ->
            oldState.copy(
                profile = args.profile.toProfile()
            )
        }
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.EditBio -> editBio(event.bio)
            is EditProfileEvent.EditName -> editName(event.name)
            is EditProfileEvent.OnUpdateProfileClick -> updateProfile()
        }
    }

    private fun editBio(bio: String) = updateProfile {
        it.copy(bio = bio)
    }

    private fun editName(name: String) = updateProfile {
        it.copy(name = name)
    }

    private fun updateProfile(update: (Profile) -> Profile) {
        _uiState.update { oldState ->
            oldState.copy(
                profile = update(oldState.profile)
            )
        }
    }

    private fun updateProfile() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1000)

            _uiState.update { oldState ->
                oldState.copy(
                    isLoading = false,
                    updateSucceed = true
                )
            }

            showSnackbar(resources.getString(R.string.profile_changed))
        }
    }
}