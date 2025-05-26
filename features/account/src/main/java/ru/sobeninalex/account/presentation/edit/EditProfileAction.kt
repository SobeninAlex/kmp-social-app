package ru.sobeninalex.account.presentation.edit

import android.net.Uri

internal sealed interface EditProfileAction {

    data class EditName(val name: String): EditProfileAction

    data class EditBio(val bio: String): EditProfileAction

    data class OnUpdateProfileClick(val imageUri: Uri = Uri.EMPTY) : EditProfileAction
}