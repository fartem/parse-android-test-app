package com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake

import android.app.Activity
import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.utils.OpenForTests
import java.util.regex.Pattern

@OpenForTests
class FakeCloudAuth : CloudAuth {

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
        signUpResult: (success: Boolean) -> Unit
    ) {
        handleAuth()
        signUpResult(true)
    }

    override fun signInWithEmail(
        username: String,
        password: String,
        signInResult: (success: Boolean) -> Unit
    ) {
        handleAuth()
        signInResult(true)
    }


    override fun signInWithFacebook(
        activity: Activity,
        signInResult: (success: Boolean) -> Unit
    ) {
        handleAuth()
        signInResult(true)
    }

    private fun handleAuth() {
        isAuth = true
    }

    override fun signInWithGoogle(
        activity: Activity,
        signInResult: (success: Boolean) -> Unit
    ) {
        handleAuth()
        signInResult(true)
    }

    override fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

    }

    override fun deleteAccount(
        afterDelete: (success: Boolean) -> Unit
    ) {
        isAuth = false
        afterDelete(true)
    }

    override fun logOut(
        afterLogOut: (success: Boolean) -> Unit
    ) {
        isAuth = false
        afterLogOut(true)
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
