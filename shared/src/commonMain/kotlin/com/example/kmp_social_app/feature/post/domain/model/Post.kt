package com.example.kmp_social_app.feature.post.domain.model

data class Post(
    val postId: String,
    val caption: String,
    val imageUrl: String?,
    val likesCount: Int,
    val commentsCount: Int,
    val createdAt: String,
    val userId: String,
    val userName: String,
    val userAvatar: String?,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false,
    val enabledLike: Boolean = true,
) {
    companion object {
        val Preview = Post(
            postId = "06dd0102-88be-4e59-8577-8a9c6ef7d1f7",
            caption = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
            imageUrl = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
            likesCount = 1,
            commentsCount = 1,
            userId = "26797bd8-3596-49a1-943b-92ec2790768f",
            createdAt = "30.04.2025 14:25",
            userName = "Sara Conor",
            userAvatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
            isLiked = false,
            isOwnPost = false,
        )

        val PreviewPostList = listOf(
            Post(
                postId = "06dd0102-88be-4e59-8577-8a9c6ef7d1f7",
                caption = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                imageUrl = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                likesCount = 1,
                commentsCount = 1,
                userId = "26797bd8-3596-49a1-943b-92ec2790768f",
                createdAt = "30.04.2025 14:25",
                userName = "Sara Conor",
                userAvatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isLiked = false,
                isOwnPost = false,
            ),
            Post(
                postId = "5e5f081f-5e2c-4a45-9157-1b73a98ec540",
                caption = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                imageUrl = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                likesCount = 2,
                commentsCount = 2,
                userId = "b5f7d534-09ee-42aa-a954-548bdbd80f69",
                createdAt = "30.04.2025 14:25",
                userName = "John Conor",
                userAvatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isLiked = true,
                isOwnPost = true,
            ),
            Post(
                postId = "bd95ce98-eb14-48da-b86b-743b26f2787b",
                caption = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                imageUrl = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                likesCount = 3,
                commentsCount = 3,
                userId = "21178fa7-09c4-4478-9aed-a1ba71873a64",
                createdAt = "30.04.2025 14:25",
                userName = "T 1000",
                userAvatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isLiked = false,
                isOwnPost = false,
            ),
            Post(
                postId = "5557acab-27af-42f4-8672-f4c3e9284cf0",
                caption = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                imageUrl = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                likesCount = 4,
                commentsCount = 4,
                userId = "37fbab13-a485-4779-81f3-cf08ee53462e",
                createdAt = "30.04.2025 14:25",
                userName = "T 800",
                userAvatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isLiked = true,
                isOwnPost = true,
            )
        )
    }
}
