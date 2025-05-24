package ru.sobeninalex.utils.preferences.user_prefs

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val id: String = "",
    val name: String = "",
    val bio: String = "",
    val avatar: String? = null,
    val token: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)