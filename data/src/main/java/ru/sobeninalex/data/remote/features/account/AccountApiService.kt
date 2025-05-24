package ru.sobeninalex.data.remote.features.account

import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import ru.sobeninalex.data.remote.KtorApiService
import ru.sobeninalex.data.remote.QueryParams
import ru.sobeninalex.data.remote.features.account.dto.ProfileInfoResponseDTO

class AccountApiService : KtorApiService() {

    suspend fun getProfileById(
        token: String,
        profileId: String,
        currentUserId: String
    ): ProfileInfoResponseDTO {
        return client.get {
            route("/user/$profileId")
            setToken(token)
            parameter(key = QueryParams.CURRENT_USER_ID, value = currentUserId)
        }
            .checkAuth()
            .body()
    }

    suspend fun updateProfile(
        token: String,
        userData: String,
        imageBytes: ByteArray?
    ): ProfileInfoResponseDTO {
        return client.submitFormWithBinaryData(
            formData = formData {
                append(key = "user_data", value = userData)
                imageBytes?.let {
                    append(
                        key = "image",
                        value = it,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, value = "image/*")
                            append(HttpHeaders.ContentDisposition, value = "filename=profile.jpg")
                        }
                    )
                }
            }
        ) {
            route("/user/update")
            setToken(token)
            contentType(ContentType.MultiPart.FormData)
            method = HttpMethod.Post
        }
            .checkAuth()
            .body<ProfileInfoResponseDTO>()
    }
}