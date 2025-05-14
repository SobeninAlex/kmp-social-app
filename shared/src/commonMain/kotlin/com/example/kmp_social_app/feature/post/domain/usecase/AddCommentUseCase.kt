package com.example.kmp_social_app.feature.post.domain.usecase

import com.example.kmp_social_app.feature.post.domain.PostRepository
import com.example.kmp_social_app.feature.post.domain.model.PostComment
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddCommentUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        postId: String,
        content: String
    ): PostComment {
        return repository.addComment(
            postId = postId,
            content = content
        )
    }
}