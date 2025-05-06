package com.example.kmp_social_app.android.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.kmp_social_app.android.common.datastore.UserSettings
import com.example.kmp_social_app.android.common.datastore.UserSettingsSerializer
import com.example.kmp_social_app.android.presentation.auth.login.LoginViewModel
import com.example.kmp_social_app.android.presentation.auth.signup.SignUpViewModel
import com.example.kmp_social_app.android.MainViewModel
import com.example.kmp_social_app.android.presentation.account.profile.ProfileViewModel
import com.example.kmp_social_app.android.presentation.home.HomeViewModel
import com.example.kmp_social_app.android.presentation.post_detail.PostDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModel {
        SignUpViewModel(
            signUpUseCase = get(),
            datastore = get()
        )
    }

    viewModelOf(::LoginViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::MainViewModel)

    viewModel { (postId: String) ->
        PostDetailViewModel(
            postId = postId
        )
    }

    viewModel { (userId: String) ->
        ProfileViewModel(
            userId = userId
        )
    }

    single<DataStore<UserSettings>> {
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                androidContext().dataStoreFile(
                    fileName = "app_user_settings"
                )
            }
        )
    }
}