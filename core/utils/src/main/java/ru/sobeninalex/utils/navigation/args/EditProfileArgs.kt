package ru.sobeninalex.utils.navigation.args

import android.os.Parcelable
import ru.sobeninalex.utils.navigation.StringSanitizer
import com.example.kmp_social_app.feature.account.domain.model.Profile
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

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

fun Profile.toProfileArgs() = ProfileArgs(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar,
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing,
    isOwnProfile = isOwnProfile,
)