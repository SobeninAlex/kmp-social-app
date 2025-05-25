package ru.sobeninalex.home.presentation.create_post

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.common.event.RefreshContentEvent
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.domain.features.post.usecase.CreatePostUseCase
import ru.sobeninalex.utils.helpers.ImageByteReader

class CreatePostViewModel(
    private val createPostUseCase: CreatePostUseCase,
    private val imageByteReader: ImageByteReader
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: CreatePostAction) = when (action) {
        is CreatePostAction.OnChangeCaption -> onChangeCaption(action.caption)
        is CreatePostAction.OnChangeImageUri -> onChangeImageUri(action.imageUri)
        is CreatePostAction.OnCreatePostClick -> createPost()
    }

    private fun onChangeCaption(caption: String) {
        _uiState.update { state ->
            state.copy(caption = caption)
        }
    }

    private fun onChangeImageUri(imageUri: Uri) {
        _uiState.update { state ->
            state.copy(imageUri = imageUri)
        }
    }

    private fun createPost() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                imageByteReader.readImageBytes(_uiState.value.imageUri)
            }.onSuccess {
                uploadPost(it)
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false) }
                throw error
            }
        }
    }

    private suspend fun uploadPost(byteArray: ByteArray) {
        runCatching {
            createPostUseCase(
                caption = _uiState.value.caption,
                imageBytes = byteArray
            )
        }.onSuccess { newPost ->
            RefreshContentEvent.sendEvent()
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    uploadPostSuccess = true
                )
            }
        }.onFailure { error ->
            _uiState.update { it.copy(isLoading = false) }
            throw error
        }
    }

}