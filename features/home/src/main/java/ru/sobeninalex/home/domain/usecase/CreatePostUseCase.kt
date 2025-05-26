package ru.sobeninalex.home.domain.usecase

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.home.domain.FeatureHomeRepository
import ru.sobeninalex.utils.helpers.SomethingWrongException

internal class CreatePostUseCase(
    private val repository: FeatureHomeRepository
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