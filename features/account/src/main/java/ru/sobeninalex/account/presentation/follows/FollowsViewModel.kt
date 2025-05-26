package ru.sobeninalex.account.presentation.follows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.account.domain.usecase.GetFollowsUseCase
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.navigation.args.FollowsArgs
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.common.presentation.DefaultPagingManager
import ru.sobeninalex.common.presentation.PagingManager
import ru.sobeninalex.utils.helpers.Constants

internal class FollowsViewModel(
    private val args: FollowsArgs,
    private val getFollowsUseCase: GetFollowsUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(FollowsUiState())
    val uiState = _uiState.asStateFlow()

    private val followsPagingManager by lazy { createFollowsPagingManager() }

    init {
        _uiState.update { it.copy(followsType = args.followsType) }
        loadContent()
    }

    fun onAction(action: FollowsAction) {
        when (action) {
            is FollowsAction.LoadMoreData -> loadData()
            is FollowsAction.Retry -> loadContent()
        }
    }

    private fun loadContent() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            followsPagingManager.loadItems()
        }
    }

    private fun createFollowsPagingManager(): PagingManager<FollowUser> {
        return DefaultPagingManager(
            onRequest = { page ->
                delay(1500) //todo: test
                getFollowsUseCase(
                    followType = args.followsType,
                    userId = args.userId,
                    page = page,
                    pageSize = Constants.DEFAULT_PAGE_SIZE
                )
            },
            onSuccess = { items, page ->
                val endReached = items.size < Constants.DEFAULT_PAGE_SIZE

                val followUsers = if (page == Constants.INITIAL_PAGE) {
                    items
                } else {
                    _uiState.value.followUsers + items
                }

                _uiState.update {
                    it.copy(
                        followUsers = followUsers,
                        endReached = endReached
                    )
                }
            },
            onFailure = { ex, page ->
                _uiState.update { it.copy(errorMessage = ex.message) }
                throw ex
            },
            onLoadStateChange = { isLoading ->
                _uiState.update { it.copy(isLoading = isLoading) }
            }
        )
    }
}