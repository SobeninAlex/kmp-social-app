package com.example.kmp_social_app.glue.data.datasource

import com.example.kmp_social_app.glue.mappers.toPost
import com.example.kmp_social_app.glue.mappers.toPostComment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.post.PostComment
import ru.sobeninalex.data.remote.services.post.PostApiDataSource
import ru.sobeninalex.data.remote.services.post.PostApiService
import ru.sobeninalex.data.remote.services.post.dto.CreatePostRequestDTO
import ru.sobeninalex.data.remote.services.post.dto.NewCommentRequestDTO
import ru.sobeninalex.data.remote.services.post.dto.PostLikeRequestDTO
import ru.sobeninalex.data.remote.services.post.dto.PostsResponseDTO
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

class PostApiDataSourceImpl(
    private val userPreferences: UserPreferences,
    private val postApiService: PostApiService,
) : PostApiDataSource {

    override suspend fun getPostsByUserId(userId: String, page: Int, pageSize: Int): List<Post> {
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
                    response.post
                        ?.toPost()
                        ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
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
                        it.toPostComment(isOwnComment = it.userId == userDate.id)
                    }
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean {
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
                    response.postComment
                        ?.toPostComment(isOwnComment = response.postComment?.userId == userDate.id)
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

    override suspend fun createPost(caption: String, imageBytes: ByteArray): Post {
        return withContext(Dispatchers.IO) {
            try {
                val userSetting = userPreferences.getUserSettings()

                val postData = Json.encodeToString(
                    serializer = CreatePostRequestDTO.serializer(),
                    value = CreatePostRequestDTO(
                        userId = userSetting.id,
                        caption = caption
                    )
                )

                val response = postApiService.createPost(
                    token = userSetting.token,
                    postData = postData,
                    imageBytes = imageBytes
                )

                if (response.isSuccess) {
                    response.post
                        ?.toPost()
                        ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw SomethingWrongException(message = ex.message)
            }
        }
    }

    override suspend fun getFeedPosts(page: Int, pageSize: Int): List<Post> {
        return fetchPosts { userSettings ->
            postApiService.getFeedPosts(
                token = userSettings.token,
                currentUserId = userSettings.id,
                page = page,
                pageSize = pageSize,
            )
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