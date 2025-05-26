package ru.sobeninalex.post_detail.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.post_detail.domain.usecase.AddCommentUseCase
import ru.sobeninalex.post_detail.domain.usecase.DeleteCommentUseCase
import ru.sobeninalex.post_detail.domain.usecase.GetPostCommentsUseCase
import ru.sobeninalex.post_detail.domain.usecase.GetPostUseCase
import ru.sobeninalex.post_detail.domain.usecase.LikeOrUnlikeUseCase
import ru.sobeninalex.post_detail.presentation.PostDetailViewModel

val InternalFeaturePostDetailModule = module {
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
    factory { GetPostCommentsUseCase(repository = get()) }
    factory { GetPostUseCase(repository = get()) }
    factory { LikeOrUnlikeUseCase(repository = get()) }
    factory { AddCommentUseCase(repository = get()) }
    factory { DeleteCommentUseCase(repository = get()) }
}