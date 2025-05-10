package com.example.kmp_social_app.android.common.utils

import com.example.kmp_social_app.common.utils.Constants

interface PagingManager<Model> {

    suspend fun loadItems()

    fun reset()
}

class DefaultPagingManager<Model>(
    private val onRequest: suspend (page: Int) -> List<Model>,
    private val onSuccess: (items: List<Model>, page: Int) -> Unit,
    private val onFailure: (ex: Exception, page: Int) -> Unit,
    private val onLoadStateChange: (isLoading: Boolean) -> Unit
) : PagingManager<Model> {

    private var currentPage = Constants.INITIAL_PAGE
    private var isLoading = false

    override suspend fun loadItems() {
        if (isLoading) return
        isLoading = true
        onLoadStateChange(true)

        try {
            val response = onRequest(currentPage)
            isLoading = false
            onLoadStateChange(false)

            onSuccess(response, currentPage)
            currentPage++
        } catch (ex: Exception) {
            isLoading = false
            onLoadStateChange(false)
            onFailure(ex, currentPage)
        }
    }

    override fun reset() {
        currentPage = Constants.INITIAL_PAGE
    }
}

