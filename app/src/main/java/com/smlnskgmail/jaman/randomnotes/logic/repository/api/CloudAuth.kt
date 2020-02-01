package com.smlnskgmail.jaman.randomnotes.logic.repository.api

import android.content.Intent
import androidx.fragment.app.Fragment

interface CloudAuth {

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

    fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )

    fun logOut(afterLogOut: (e: Exception?) -> Unit)

}
