package ru.sobeninalex.domain.di

import org.koin.dsl.module
import ru.sobeninalex.domain.features.post.usecase.AddCommentUseCase
import ru.sobeninalex.domain.features.post.usecase.CreatePostUseCase
import ru.sobeninalex.domain.features.post.usecase.DeleteCommentUseCase
import ru.sobeninalex.domain.features.post.usecase.GetFeedPostsUseCase
import ru.sobeninalex.domain.features.post.usecase.GetPostCommentsUseCase
import ru.sobeninalex.domain.features.post.usecase.GetPostUseCase
import ru.sobeninalex.domain.features.post.usecase.GetPostsByUserIdUseCase
import ru.sobeninalex.domain.features.post.usecase.LikeOrUnlikeUseCase

val PostDomainModule = module {
    factory { AddCommentUseCase(repository = get()) }
    factory { DeleteCommentUseCase(repository = get()) }
    factory { GetFeedPostsUseCase(repository = get()) }
    factory { GetPostCommentsUseCase(repository = get()) }
    factory { GetPostsByUserIdUseCase(repository = get()) }
    factory { GetPostUseCase(repository = get()) }
    factory { LikeOrUnlikeUseCase(repository = get()) }
    factory { CreatePostUseCase(repository = get()) }
}