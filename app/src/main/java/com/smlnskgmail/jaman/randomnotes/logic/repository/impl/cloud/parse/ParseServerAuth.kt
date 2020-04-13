package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.parse

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import java.util.regex.Pattern

class ParseServerAuth : CloudAuth {

    companion object {

        const val googleAuthRequest = 101

        private const val minimumPasswordLength = 8
        private const val maximumPasswordLength = 32

    }

    private var googleAuthCallback: GoogleAuthCallback? = null

    override fun isAuthorized(): Boolean {
        return ParseUser.getCurrentUser() != null
    }

    override fun logInWithFacebook(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    ) {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(
            activity,
            listOf("public_profile")
        ) { _, e -> afterFacebookLogin(e) }
    }

    override fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        afterLogin: (e: Exception?) -> Unit
    ) {
        val parseUser = ParseUser()
        parseUser.username = username
        parseUser.email = email
        parseUser.setPassword(password)
        parseUser.signUpInBackground {
            afterLogin(it)
        }
    }

    override fun signInWithEmail(
        username: String,
        password: String,
        afterRegister: (e: Exception?) -> Unit
    ) {
        ParseUser.logInInBackground(username, password) { _, e ->
            afterRegister(e)
        }
    }

    override fun logInWithGoogle(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    ) {
        val signInOptions = getGoogleSignInOptions(activity)
        val signInClient = GoogleSignIn.getClient(
            activity,
            signInOptions
        )

        googleAuthCallback = object : GoogleAuthCallback {
            override fun sendResult(exception: Exception?) {
                afterFacebookLogin(exception)
            }
        }

        activity.startActivityForResult(
            signInClient.signInIntent,
            googleAuthRequest
        )
    }

    private fun getGoogleSignInOptions(
        context: Context
    ): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_web_app_token_id))
            .requestEmail()
            .build()
    }

    override fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == googleAuthRequest) {
            bindForGoogle(data)
        } else {
            ParseFacebookUtils.onActivityResult(
                requestCode,
                resultCode,
                data
            )
        }
    }

    private fun bindForGoogle(data: Intent?) {
        try {
            val signInAccount = GoogleSignIn.getSignedInAccountFromIntent(data)
                    .getResult(ApiException::class.java)
            if (signInAccount != null) {
                val authData: MutableMap<String, String?> = HashMap()
                authData["id"] = signInAccount.id
                authData["id_token"] = signInAccount.idToken
                ParseUser.logInWithInBackground("google", authData)
                    .continueWith<Any?> {
                        if (it.result == null) {
                            googleAuthCallback!!.sendResult(null)
                        } else {
                            googleAuthCallback!!.sendResult(
                                IllegalStateException(
                                    "Google auth error!"
                                )
                            )
                        }
                    }
            } else {
                googleAuthCallback!!.sendResult(
                    NullPointerException(
                        "Google account must not be null!"
                    )
                )
            }
        } catch (e: ApiException) {
            googleAuthCallback!!.sendResult(e)
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

    private interface GoogleAuthCallback {

        fun sendResult(exception: Exception?)

    }

}
