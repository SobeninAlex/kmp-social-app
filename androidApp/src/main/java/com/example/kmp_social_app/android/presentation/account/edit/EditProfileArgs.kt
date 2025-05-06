package com.example.kmp_social_app.android.presentation.account.edit

import android.os.Parcelable
import com.example.kmp_social_app.android.common.navigation.StringSanitizer
import com.example.kmp_social_app.feature.profile.domain.model.Profile
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class EditProfileArgs(
    val profile: ProfileArgs
) : Parcelable

@Serializable
@Parcelize
data class ProfileArgs(
    val id: String,
    val name: String,
    @Serializable(with = StringSanitizer::class) val email: String,
    @Serializable(with = StringSanitizer::class) val bio: String,
    @Serializable(with = StringSanitizer::class) val avatar: String?,
    val followersCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean = false,
    val isOwnProfile: Boolean = false,
): Parcelable

fun ProfileArgs.toProfile() = Profile(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar,
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing
)

fun Profile.toProfileArgs() = ProfileArgs(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar,
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing
)