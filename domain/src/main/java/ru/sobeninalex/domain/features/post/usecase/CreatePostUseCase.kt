package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.Post
import ru.sobeninalex.utils.helpers.SomethingWrongException

class CreatePostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        caption: String,
        imageBytes: ByteArray
    ) : Post {
        if (caption.isBlank() || caption.length > 300) {
            throw SomethingWrongException(message = "Description length do not more than 300 symbols")
        }
        return repository.createPost(
            caption = caption,
            imageBytes = imageBytes
        )
    }
}