package com.smlnskgmail.jaman.randomnotes.repository.model.cloud

import android.content.Intent
import androidx.fragment.app.Fragment
import com.smlnskgmail.jaman.randomnotes.repository.entities.Note

interface CloudDataSource {

    fun saveAllNotes(
        notes: List<Note>,
        errorOnSave: (e: Exception) -> Unit
    )

    fun restoreAllNotes(
        notes: List<Note>,
        afterRestore: (newNotes: List<Note>, e: Exception?) -> Unit
    )

    fun isAuthorized(): Boolean

    fun signInWithFacebook(
        fragment: Fragment,
        afterFacebookLogin: (e: Exception) -> Unit
    )

    fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        afterLogin: (e: Exception?) -> Unit
    )

    fun signInWithEmail(
        username: String,
        password: String,
        afterRegister: (e: Exception?) -> Unit
    )

    fun bindForAuth(requestCode: Int, resultCode: Int, data: Intent?)

    fun logOut(afterLogOut: (e: Exception?) -> Unit)

}