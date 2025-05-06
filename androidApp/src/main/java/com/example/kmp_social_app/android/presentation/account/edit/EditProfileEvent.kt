package com.example.kmp_social_app.android.presentation.account.edit

sealed interface EditProfileEvent {

    data class EditName(val name: String): EditProfileEvent

    data class EditBio(val bio: String): EditProfileEvent

    data object OnUpdateProfileClick : EditProfileEvent
}