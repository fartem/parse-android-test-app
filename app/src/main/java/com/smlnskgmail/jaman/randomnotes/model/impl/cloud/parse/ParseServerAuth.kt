package com.smlnskgmail.jaman.randomnotes.model.impl.cloud.parse

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.parse.LogInCallback
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.parse.google.ParseGoogleUtils
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import java.util.regex.Pattern

class ParseServerAuth(
    private val context: Context
) : CloudAuth {

    companion object {

        private const val minimumPasswordLength = 8
        private const val maximumPasswordLength = 32

    }

    override fun isAuthorized(): Boolean {
        return ParseUser.getCurrentUser() != null
    }

    override fun signInWithFacebook(
        activity: Activity,
        signInResult: (e: Exception?) -> Unit
    ) {
        ParseFacebookUtils.initialize(
            context
        )
        ParseFacebookUtils.logInWithReadPermissionsInBackground(
            activity,
            listOf("public_profile")
        ) { _, e -> signInResult(e) }
    }

    override fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        signUpResult: (e: Exception?) -> Unit
    ) {
        val parseUser = ParseUser()
        parseUser.username = username
        parseUser.email = email
        parseUser.setPassword(password)
        parseUser.signUpInBackground {
            signUpResult(it)
        }
    }

    override fun signInWithEmail(
        username: String,
        password: String,
        signInResult: (e: Exception?) -> Unit
    ) {
        ParseUser.logInInBackground(username, password) { _, e ->
            signInResult(e)
        }
    }

    override fun signInWithGoogle(
        activity: Activity,
        signInResult: (e: Exception?) -> Unit
    ) {
        ParseGoogleUtils.initialize(
            context.getString(
                R.string.google_web_app_token_id
            )
        )
        ParseGoogleUtils.logIn(
            activity,
            LogInCallback { user, e ->
                signInResult(e)
            }
        )
    }

    override fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        ParseGoogleUtils.onActivityResult(
            requestCode,
            resultCode,
            data
        )
        ParseFacebookUtils.onActivityResult(
            requestCode,
            resultCode,
            data
        )
    }

    override fun deleteAccount(
        afterDelete: (e: Exception?) -> Unit
    ) {
        ParseUser.getCurrentUser()?.deleteInBackground {
            afterDelete(it)
        }
    }

    override fun logOut(afterLogOut: (e: Exception?) -> Unit) {
        val parseUser = ParseUser.getCurrentUser()
        if (ParseFacebookUtils.isLinked(parseUser)) {
            AccessToken.setCurrentAccessToken(null)
            if (LoginManager.getInstance() != null) {
                LoginManager.getInstance().logOut()
            }
        }
        ParseUser.logOutInBackground {
            afterLogOut(it)
        }
    }

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

}
