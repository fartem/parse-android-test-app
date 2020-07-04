package com.smlnskgmail.jaman.randomnotes.model.api.cloud

import android.app.Activity
import android.content.Intent

interface CloudAuth {

    fun isAuthorized(): Boolean

    fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        signUpResult: (e: Exception?) -> Unit
    )

    fun signInWithEmail(
        username: String,
        password: String,
        signInResult: (e: Exception?) -> Unit
    )

    fun signInWithGoogle(
        activity: Activity,
        signInResult: (e: Exception?) -> Unit
    )

    fun signInWithFacebook(
        activity: Activity,
        signInResult: (e: Exception?) -> Unit
    )

    fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )

    fun deleteAccount(afterDelete: (e: Exception?) -> Unit)

    fun logOut(afterLogOut: (e: Exception?) -> Unit)

    fun isValidEmail(email: String): Boolean

    fun isValidPassword(password: String): Boolean

    fun passwordMinimumLength(): Int
    fun passwordMaximumLength(): Int

}
