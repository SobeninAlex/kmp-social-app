package ru.sobeninalex.common.navigation.args

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.sobeninalex.domain.features.follows.model.FollowsType

@Serializable
@Parcelize
data class FollowsArgs(
    val userId: String,
    val followsType: FollowsType,
): Parcelable