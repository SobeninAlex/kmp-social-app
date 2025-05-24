package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.PostComment

class GetPostCommentsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        postId: String,
        page: Int,
        pageSize: Int
    ) : List<PostComment> {
        return repository.getPostComments(
            postId = postId,
            page = page,
            pageSize = pageSize
        )
    }
}