package com.smlnskgmail.jaman.randomnotes.model.api.cloud

import android.app.Activity
import android.content.Intent

interface CloudAuth {

    fun isAuthorized(): Boolean

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

    fun logInWithGoogle(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    )

    fun logInWithFacebook(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    )

    fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )

    fun logOut(afterLogOut: (e: Exception?) -> Unit)

    fun isValidEmail(email: String): Boolean

    fun isValidPassword(password: String): Boolean

    fun passwordMinimumLength(): Int
    fun passwordMaximumLength(): Int

}
