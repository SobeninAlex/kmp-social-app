package ru.sobeninalex.account.presentation.edit

sealed interface EditProfileAction {

    data class EditName(val name: String): EditProfileAction

    data class EditBio(val bio: String): EditProfileAction

    data object OnUpdateProfileClick : EditProfileAction
}