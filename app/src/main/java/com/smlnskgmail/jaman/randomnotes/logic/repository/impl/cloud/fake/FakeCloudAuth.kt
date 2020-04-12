package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake

import android.app.Activity
import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import java.util.regex.Pattern

open class FakeCloudAuth : CloudAuth {

    companion object {

        private const val minimumPasswordLength = 8
        private const val maximumPasswordLength = 32

    }

    private var isAuth = false

    override fun isAuthorized(): Boolean {
        return isAuth
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

    // CPD-OFF
    override fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
        )
        return pattern.matcher(email).find()
    }

    override fun isValidPassword(password: String): Boolean {
        val length = password.length
        return password.trim().length == length
                && length >= minimumPasswordLength
                && length <= maximumPasswordLength
    }

    override fun passwordMinimumLength(): Int {
        return minimumPasswordLength
    }

    override fun passwordMaximumLength(): Int {
        return maximumPasswordLength
    }
    // CPD-ON

}
