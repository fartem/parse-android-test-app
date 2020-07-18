package com.smlnskgmail.jaman.randomnotes.model.api.cloud

import android.app.Activity
import android.content.Intent

interface CloudAuth {

    fun isAuthorized(): Boolean

    fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        signUpResult: (success: Boolean) -> Unit
    )

    fun signInWithEmail(
        username: String,
        password: String,
        signInResult: (success: Boolean)-> Unit
    )

    fun signInWithGoogle(
        activity: Activity,
        signInResult: (success: Boolean) -> Unit
    )

    fun signInWithFacebook(
        activity: Activity,
        signInResult: (success: Boolean) -> Unit
    )

    fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )

    fun deleteAccount(afterDelete: (success: Boolean) -> Unit)

    fun logOut(afterLogOut: (success: Boolean) -> Unit)

    fun isValidEmail(email: String): Boolean

    fun isValidPassword(password: String): Boolean

    fun passwordMinimumLength(): Int
    fun passwordMaximumLength(): Int

}
