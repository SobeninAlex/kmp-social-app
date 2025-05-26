package com.example.kmp_social_app.glue.features.authorization

import com.example.kmp_social_app.glue.mappers.toAuthResult
import ru.sobeninalex.authorization.domain.FeatureAuthorizationRepository
import ru.sobeninalex.common.models.auth.AuthResult
import ru.sobeninalex.data.remote.services.authorization.AuthApiDataSource

class FeatureAuthorizationRepositoryImpl(
    private val authApiDataSource: AuthApiDataSource,
) : FeatureAuthorizationRepository {

    override suspend fun signUp(name: String, email: String, password: String): AuthResult {
        return authApiDataSource.signUp(
            name = name,
            email = email,
            password = password
        ).toAuthResult()
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        return authApiDataSource.signIn(
            email = email,
            password = password
        ).toAuthResult()
    }
}
