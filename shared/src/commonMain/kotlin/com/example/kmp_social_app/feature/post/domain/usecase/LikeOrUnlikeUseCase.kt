package com.example.kmp_social_app.feature.post.domain.usecase

import com.example.kmp_social_app.common.utils.NetworkResponse
import com.example.kmp_social_app.feature.post.domain.PostRepository
import com.example.kmp_social_app.feature.post.domain.model.Post
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LikeOrUnlikeUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        post: Post
    ): NetworkResponse<Boolean> {
        return repository.likeOrUnlikePost(
            postId = post.postId,
            shouldLike = !post.isLiked
        )
    }
}