package com.example.kmp_social_app.feature.post.domain.usecase

import com.example.kmp_social_app.feature.post.domain.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteCommentUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        commentId: String,
        postId: String,
    ) {
        return repository.deleteComment(
            commentId = commentId,
            postId = postId
        )
    }
}