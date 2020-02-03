package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.parse

import android.app.Activity
import android.content.Context
import android.content.Intent
import bolts.Task
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.parse.ParseException
import com.parse.ParseUser
import com.parse.SaveCallback
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth

class ParseAuth : CloudAuth {

    private var googleAuthCallback: GoogleAuthCallback? = null

    companion object {

        const val googleAuthRequest = 101

    }

    override fun isAuthorized(): Boolean {
        return ParseUser.getCurrentUser() != null
    }

    override fun logInWithGoogle(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    ) {
        val signInOptions = getGoogleSignInOptions(activity)
        val signInClient = GoogleSignIn.getClient(activity, signInOptions)

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

    private fun getGoogleSignInOptions(context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_web_app_token_id))
            .requestEmail()
            .build()
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

    override fun bindForAuth(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == googleAuthRequest) {
            bindForGoogle(data)
        } else {
            ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
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
                val email = signInAccount.email
                ParseUser.logInWithInBackground("google", authData)
                    .continueWith<Any?> { task: Task<ParseUser?> ->
                        val parseUser = task.result
                        if (parseUser != null) {
                            parseUser.email = email
                            parseUser.saveInBackground(SaveCallback { e: ParseException? ->
                                googleAuthCallback!!.sendResult(e)
                            })
                        }
                        null
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

    private interface GoogleAuthCallback {

        fun sendResult(exception: Exception?)

    }

}
