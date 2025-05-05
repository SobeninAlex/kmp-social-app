package com.example.kmp_social_app.feature.follows.domain.model

data class FollowUser(
    val id: String,
    val name: String,
    val bio: String,
    val avatar: String?,
    val isFollowing: Boolean,
) {

    companion object {
        val FAKE = FollowUser(
            id = "26797bd8-3596-49a1-943b-92ec2790768f",
            name = "Sara Conor",
            bio = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
            avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
            isFollowing = false
        )

        val FAKE_LIST = listOf(
            FollowUser(
                id = "26797bd8-3596-49a1-943b-92ec2790768f",
                name = "Sara Conor",
                bio = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isFollowing = false
            ),
            FollowUser(
                id = "b5f7d534-09ee-42aa-a954-548bdbd80f69",
                name = "John Conor",
                bio = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isFollowing = true
            ),
            FollowUser(
                id = "21178fa7-09c4-4478-9aed-a1ba71873a64",
                name = "T 1000",
                bio = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isFollowing = false
            ),
            FollowUser(
                id = "37fbab13-a485-4779-81f3-cf08ee53462e",
                name = "T 800",
                bio = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
                avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
                isFollowing = true
            )
        )
    }
}
