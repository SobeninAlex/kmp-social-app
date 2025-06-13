package ru.sobeninalex.account.presentation.edit

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.account.domain.usecase.UpdateProfileUseCase
import ru.sobeninalex.common.event.profile.ProfileUpdatedSharedFlowEvent
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.common.navigation.args.EditProfileArgs
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.utils.helpers.ImageByteReader

internal class EditProfileViewModel(
    private val args: EditProfileArgs,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val imageByteReader: ImageByteReader
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { oldState ->
            oldState.copy(
                profile = args.profile
            )
        }
    }

    fun onAction(action: EditProfileAction) {
        when (action) {
            is EditProfileAction.EditBio -> editBio(action.bio)
            is EditProfileAction.EditName -> editName(action.name)
            is EditProfileAction.OnUpdateProfileClick -> readImageByte(action.imageUri)
        }
    }

    private fun readImageByte(imageUri: Uri) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            if (imageUri == Uri.EMPTY) {
                onUpdateProfile()
                return@launch
            }

            runCatching {
                imageByteReader.readImageBytes(listOf(imageUri))
            }.onSuccess { result ->
                onUpdateProfile(byteArray = result.first())
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false) }
                throw error
            }
        }
    }

    private suspend fun onUpdateProfile(byteArray: ByteArray? = null) {
        runCatching {
            updateProfileUseCase(
                profile = uiState.value.profile,
                imageBytes = byteArray
            )
        }.onSuccess {
            ProfileUpdatedSharedFlowEvent.sendEvent(it)
            _uiState.update { oldState ->
                oldState.copy(
                    isLoading = false,
                    updateSucceed = true
                )
            }
            showSnackbar("Profile changed success!")
        }.onFailure { error ->
            _uiState.update { it.copy(isLoading = false) }
            throw error
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