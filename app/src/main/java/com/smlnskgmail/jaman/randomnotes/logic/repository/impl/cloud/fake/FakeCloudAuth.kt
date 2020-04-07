package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake

import android.app.Activity
import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth

open class FakeCloudAuth : CloudAuth {

    private var isAuth = false

    override fun isAuthorized(): Boolean {
        return isAuth
    }

    override fun logInWithFacebook(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    ) {
        handleAuth()
        afterFacebookLogin(null)
    }

    private fun handleAuth() {
        isAuth = true
    }

    override fun logInWithGoogle(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    ) {
        handleAuth()
        afterFacebookLogin(null)
    }

    override fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        afterLogin: (e: Exception?) -> Unit
    ) {
        handleAuth()
        afterLogin(null)
    }

    override fun signInWithEmail(
        username: String,
        password: String,
        afterRegister: (e: Exception?) -> Unit
    ) {
        handleAuth()
        afterRegister(null)
    }

    override fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

    }

    override fun logOut(
        afterLogOut: (e: Exception?) -> Unit
    ) {
        isAuth = false
        afterLogOut(null)
    }

}
