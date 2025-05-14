package com.example.kmp_social_app.di

import com.example.kmp_social_app.feature.post.data.PostApiService
import com.example.kmp_social_app.feature.post.data.PostRepositoryImpl
import com.example.kmp_social_app.feature.post.domain.PostRepository
import com.example.kmp_social_app.feature.post.domain.usecase.AddCommentUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.DeleteCommentUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetFeedPostsUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetPostCommentsUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetPostUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetPostsByUserIdUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.LikeOrUnlikeUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val postModule = module {
    singleOf(::PostRepositoryImpl).bind<PostRepository>()
    factory { PostApiService() }
    factory { GetFeedPostsUseCase() }
    factory { LikeOrUnlikeUseCase() }
    factory { GetPostsByUserIdUseCase() }
    factory { AddCommentUseCase() }
    factory { DeleteCommentUseCase() }
    factory { GetPostCommentsUseCase() }
    factory { GetPostUseCase() }
}