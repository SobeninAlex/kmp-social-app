package ru.sobeninalex.account.presentation.edit

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.common.navigation.args.EditProfileArgs
import ru.sobeninalex.common.navigation.args.toProfile
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.domain.features.account.model.Profile

class EditProfileViewModel(
    private val args: EditProfileArgs
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { oldState ->
            oldState.copy(
                profile = args.profileArgs.toProfile()
            )
        }
    }

    fun onAction(action: EditProfileAction) {
        when (action) {
            is EditProfileAction.EditBio -> editBio(action.bio)
            is EditProfileAction.EditName -> editName(action.name)
            is EditProfileAction.OnUpdateProfileClick -> onUpdateProfile()
        }
    }

    private fun onUpdateProfile() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1000)

            _uiState.update { oldState ->
                oldState.copy(
                    isLoading = false,
                    updateSucceed = true
                )
            }

            showSnackbar("Profile changed success!")
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
}