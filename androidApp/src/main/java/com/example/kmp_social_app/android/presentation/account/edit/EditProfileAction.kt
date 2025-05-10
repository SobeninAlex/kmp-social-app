package com.example.kmp_social_app.android.presentation.account.edit

sealed interface EditProfileAction {

    data class EditName(val name: String): EditProfileAction

    data class EditBio(val bio: String): EditProfileAction

    data object OnUpdateProfileClick : EditProfileAction
}