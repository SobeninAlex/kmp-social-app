package ru.sobeninalex.post_detail.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.post_detail.presentation.PostDetailViewModel

val PostDetailFeatureModule = module {
    viewModel { (postId: String) ->
        PostDetailViewModel(
            postId = postId,
            getPostCommentsUseCase = get(),
            getPostUseCase = get(),
            likeOrUnlikeUseCase = get(),
            addCommentUseCase = get(),
            deleteCommentUseCase = get(),
        )
    }
}