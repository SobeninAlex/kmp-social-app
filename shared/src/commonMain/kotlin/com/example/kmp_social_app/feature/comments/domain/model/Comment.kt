package com.example.kmp_social_app.feature.comments.domain.model

data class Comment(
    val id: String,
    val postId: String,
    val userId: String,
    val content: String,
    val userName: String,
    val avatar: String?,
    val createdAt: String,
) {

    companion object {
        val Fake = Comment(
            id = "8febfd22-b352-4e12-9951-433f368372ee",
            postId = "06dd0102-88be-4e59-8577-8a9c6ef7d1f7",
            userId = "26797bd8-3596-49a1-943b-92ec2790768f",
            content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
            userName = "Sara Conor",
            avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
            createdAt = "30.04.2025 14:25"
        )

        val FakeList = listOf(
            Comment(
                id = "8febfd22-b352-4e12-9951-433f368372ee",
                postId = "06dd0102-88be-4e59-8577-8a9c6ef7d1f7",
                userId = "26797bd8-3596-49a1-943b-92ec2790768f",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "Sara Conor",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
            Comment(
                id = "126ad728-d96c-4652-b252-c71007b4aecd",
                postId = "06dd0102-88be-4e59-8577-8a9c6ef7d1f7",
                userId = "26797bd8-3596-49a1-943b-92ec2790768f",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "Sara Conor",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
            Comment(
                id = "0cd63bd1-fa56-4dd8-8334-2e372f973c3c",
                postId = "06dd0102-88be-4e59-8577-8a9c6ef7d1f7",
                userId = "26797bd8-3596-49a1-943b-92ec2790768f",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "Sara Conor",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
            Comment(
                id = "a3a15a77-9949-49b1-bcff-f0e187ab8c4a",
                postId = "06dd0102-88be-4e59-8577-8a9c6ef7d1f7",
                userId = "26797bd8-3596-49a1-943b-92ec2790768f",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                userName = "Sara Conor",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                createdAt = "30.04.2025 14:25"
            ),
        )
    }
}