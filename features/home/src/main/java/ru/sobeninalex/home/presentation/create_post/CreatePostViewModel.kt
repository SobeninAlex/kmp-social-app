package ru.sobeninalex.home.presentation.create_post

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.common.event.other.RefreshContentSharedFlowEvent
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.home.domain.usecase.CreatePostUseCase
import ru.sobeninalex.utils.helpers.ImageByteReader

internal class CreatePostViewModel(
    private val createPostUseCase: CreatePostUseCase,
    private val imageByteReader: ImageByteReader
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: CreatePostAction) = when (action) {
        is CreatePostAction.OnChangeCaption -> onChangeCaption(action.caption)
        is CreatePostAction.OnCreatePostClick -> createPost()
        is CreatePostAction.OnPickAttachments -> onPickAttachments(action.uris)
        is CreatePostAction.HideMediaPager -> hideMediaPager()
        is CreatePostAction.ShowMediaPager -> showMediaPager(uri = action.uri)
    }

    private fun showMediaPager(uri: Uri) {
        _uiState.update { state ->
            state.copy(mediaPagerState = MediaPagerState(show = true, uri = uri))
        }
    }

    private fun hideMediaPager() {
        _uiState.update { state ->
            state.copy(mediaPagerState = MediaPagerState(show = false))
        }
    }

    private fun onChangeCaption(caption: String) {
        _uiState.update { state ->
            state.copy(caption = caption)
        }
    }

    private fun onPickAttachments(uris: List<Uri>) {
        _uiState.update { state ->
            state.copy(
                attachmentsUri = state.attachmentsUri + uris
            )
        }
    }

    private fun createPost() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            runCatching {
                imageByteReader.readImageBytes(_uiState.value.attachmentsUri)
            }.onSuccess {
                uploadPost(it)
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false) }
                throw error
            }
        }
    }

    private suspend fun uploadPost(byteArray: List<ByteArray>) {
        runCatching {
            createPostUseCase(
                caption = _uiState.value.caption,
                imagesBytes = byteArray
            )
        }.onSuccess { _ ->
            RefreshContentSharedFlowEvent.sendEvent()
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