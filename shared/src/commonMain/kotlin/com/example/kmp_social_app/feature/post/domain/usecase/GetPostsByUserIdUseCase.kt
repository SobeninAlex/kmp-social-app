package com.example.kmp_social_app.feature.post.domain.usecase

import com.example.kmp_social_app.feature.post.domain.PostRepository
import com.example.kmp_social_app.feature.post.domain.model.Post
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetPostsByUserIdUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        userId: String,
        page: Int,
        pageSize: Int
    ): List<Post> {
        return repository.getPostsByUserId(
            userId = userId,
            page = page,
            pageSize = pageSize
        )
    }
}