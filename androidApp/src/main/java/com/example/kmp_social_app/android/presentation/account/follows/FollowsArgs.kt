package com.example.kmp_social_app.android.presentation.account.follows

import android.os.Parcelable
import com.example.kmp_social_app.feature.follows.domain.model.FollowsType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class FollowsArgs(
    val userId: String,
    val followsType: FollowsType,
): Parcelable