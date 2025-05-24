package ru.sobeninalex.domain.features.account

import org.koin.dsl.module
import ru.sobeninalex.domain.features.account.usecase.GetProfileUseCase
import ru.sobeninalex.domain.features.account.usecase.UpdateProfileUseCase

internal val AccountFeatureModule = module {
    factory {
        GetProfileUseCase(repository = get())
    }

    factory {
        UpdateProfileUseCase(repository = get())
    }
}