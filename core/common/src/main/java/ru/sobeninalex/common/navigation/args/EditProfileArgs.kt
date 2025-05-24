package ru.sobeninalex.common.navigation.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.common.navigation.StringSanitizer

@Serializable
@Parcelize
class EditProfileArgs(
    val profileArgs: ProfileArgs
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

fun Profile.toProfileArgs() = ProfileArgs(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar,
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing,
    isOwnProfile = isOwnProfile
)

fun ProfileArgs.toProfile() = Profile(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar,
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing,
    isOwnProfile = isOwnProfile
)