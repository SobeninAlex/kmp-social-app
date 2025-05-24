package com.example.kmp_social_app.di

import com.example.kmp_social_app.presentation.main.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel {
        MainViewModel(
            dataStoreUserSettings = get()
        )
    }
}