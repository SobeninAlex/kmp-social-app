package com.example.kmp_social_app.feature.profile.domain.model

data class Profile(
    val id: String,
    val name: String,
    val email: String,
    val bio: String,
    val avatar: String?,
    val followersCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean = false,
    val isOwnProfile: Boolean = false,
) {
    
    companion object {
        val Preview = Profile(
            id = "26797bd8-3596-49a1-943b-92ec2790768f",
            name = "Sara Conor",
            email = "test@test.com",
            bio = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy",
            avatar = "https://avatars.mds.yandex.net/i?id=37d517f2000da3eb5adbc7c18179ce11_l-5232111-images-thumbs&n=13",
            followersCount = 12,
            followingCount = 10,
            isFollowing = false,
            isOwnProfile = true
        )

        val Default = Profile(
            id = "",
            name = "",
            email = "",
            bio = "",
            avatar = "",
            followersCount = 0,
            followingCount = 0,
            isFollowing = false,
            isOwnProfile = false
        )
    }
}
