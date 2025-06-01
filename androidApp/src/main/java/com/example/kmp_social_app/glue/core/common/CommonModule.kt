package com.example.kmp_social_app.glue.core.common

import org.koin.dsl.module
import ru.sobeninalex.common.presentation.CustomCoroutineScope

val CommonModule = module {
    single { CustomCoroutineScope }
}