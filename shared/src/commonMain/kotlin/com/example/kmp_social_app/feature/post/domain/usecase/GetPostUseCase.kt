package com.example.kmp_social_app.feature.post.domain.usecase

import com.example.kmp_social_app.feature.post.domain.PostRepository
import com.example.kmp_social_app.feature.post.domain.model.Post
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPostUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        postId: String,
        userId: String
    ): Post {
        return repository.getPost(postId = postId, userId = userId)
    }
}