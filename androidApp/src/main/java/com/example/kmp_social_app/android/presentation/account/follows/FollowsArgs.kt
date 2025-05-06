package com.example.kmp_social_app.android.presentation.account.follows

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class FollowsArgs(
    val userId: String,
    val followsType: FollowsType,
): Parcelable

enum class FollowsType(val value: String) {
    FOLLOWERS("Followers"),
    FOLLOWING("Following")
}