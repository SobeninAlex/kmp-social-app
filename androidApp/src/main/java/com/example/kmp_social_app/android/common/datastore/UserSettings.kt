package com.example.kmp_social_app.android.common.datastore

import androidx.datastore.core.Serializer
import com.example.kmp_social_app.feature.auth.domain.model.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

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

object UserSettingsSerializer: Serializer<UserSettings> {
    override val defaultValue: UserSettings
        get() = UserSettings()

    override suspend fun readFrom(input: InputStream): UserSettings {
        return try {
            Json.decodeFromString(
                deserializer = UserSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (ex: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserSettings, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = UserSettings.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}

fun UserSettings.toAuthResult(): AuthResult {
    return AuthResult(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}

fun AuthResult.toUserSettings(): UserSettings {
    return UserSettings(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}