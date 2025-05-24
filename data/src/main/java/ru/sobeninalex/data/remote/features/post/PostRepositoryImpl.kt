package ru.sobeninalex.data.remote.features.post

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings
import ru.sobeninalex.data.remote.features.post.dto.NewCommentRequestDTO
import ru.sobeninalex.data.remote.features.post.dto.PostLikeRequestDTO
import ru.sobeninalex.data.remote.features.post.dto.PostsResponseDTO
import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.Post
import ru.sobeninalex.domain.features.post.model.PostComment
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException

internal class PostRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val postApiService: PostApiService,
) : PostRepository {

    override suspend fun likeOrUnlikePost(
        postId: String,
        shouldLike: Boolean
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val userDate = userPreferences.getUserSettings()

                val request = PostLikeRequestDTO(
                    postId = postId,
                    userId = userDate.id
                )

                val response = if (shouldLike) {
                    postApiService.likePost(
                        token = userDate.token,
                        request = request
                    )
                } else {
                    postApiService.unlikePost(
                        token = userDate.token,
                        request = request
                    )
                }

                if (response.isSuccess) {
                    true
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun getFeedPosts(
        page: Int,
        pageSize: Int,
    ): List<Post> {
        return fetchPosts { userSettings ->
            postApiService.getFeedPosts(
                token = userSettings.token,
                currentUserId = userSettings.id,
                page = page,
                pageSize = pageSize,
            )
        }
    }

    override suspend fun getPost(postId: String): Post {
        return withContext(Dispatchers.IO) {
            try {
                val userDate = userPreferences.getUserSettings()

                val response = postApiService.getPost(
                    token = userDate.token,
                    postId = postId,
                    currentUserId = userDate.id
                )

                if (response.isSuccess) {
                    response.post?.toPost()
                        ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun getPostsByUserId(
        userId: String,
        page: Int,
        pageSize: Int
    ): List<Post> {
        return fetchPosts { userSettings ->
            postApiService.getPostsByUserId(
                token = userSettings.token,
                userId = userId,
                currentUserId = userSettings.id,
                page = page,
                pageSize = pageSize
            )
        }
    }

    override suspend fun getPostComments(
        postId: String,
        page: Int,
        pageSize: Int
    ): List<PostComment> {
        return withContext(Dispatchers.IO) {
            try {
                val userDate = userPreferences.getUserSettings()

                val response = postApiService.getPostComments(
                    token = userDate.token,
                    postId = postId,
                    page = page,
                    pageSize = pageSize
                )

                if (response.isSuccess) {
                    response.postComments.map {
                        it.toPostComment(
                            isOwnComment = it.userId == userDate.id
                        )
                    }
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun addComment(postId: String, content: String): PostComment {
        return withContext(Dispatchers.IO) {
            try {
                val userDate = userPreferences.getUserSettings()

                if (content.isBlank()) {
                    throw SomethingWrongException(message = "Comment content cannot be empty")
                }

                val request = NewCommentRequestDTO(
                    postId = postId,
                    userId = userDate.id,
                    content = content.trim()
                )

                val response = postApiService.addComment(
                    token = userDate.token,
                    request = request
                )

                if (response.isSuccess) {
                    response.postComment?.toPostComment(
                        isOwnComment = response.postComment.userId == userDate.id
                    )
                        ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun deleteComment(commentId: String, postId: String) {
        return withContext(Dispatchers.IO) {
            try {
                val userDate = userPreferences.getUserSettings()

                val response = postApiService.deleteComment(
                    token = userDate.token,
                    commentId = commentId,
                    postId = postId,
                    userId = userDate.id
                )

                if (!response.isSuccess) {
                    throw SomethingWrongException(message = response.errorMessage)
                } else Unit
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    private suspend fun fetchPosts(
        apiCall: suspend (UserSettings) -> PostsResponseDTO
    ): List<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val userDate = userPreferences.getUserSettings()

                val response = apiCall(userDate)

                if (response.isSuccess) {
                    response.posts.map { it.toPost() }
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }
}