package ru.sobeninalex.domain.features.authorization

import org.koin.dsl.module
import ru.sobeninalex.domain.features.authorization.usecase.*

internal val AuthorizationFeatureModule = module {
    factory {
        SignUpUseCase(repository = get())
    }

    factory {
        LoginUseCase(repository = get())
    }
}