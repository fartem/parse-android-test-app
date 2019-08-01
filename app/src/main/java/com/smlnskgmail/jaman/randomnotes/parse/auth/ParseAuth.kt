package com.smlnskgmail.jaman.randomnotes.parse.auth

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.parse.auth.data.AuthCallback
import com.smlnskgmail.jaman.randomnotes.parse.auth.data.AuthStatus
import com.smlnskgmail.jaman.randomnotes.parse.auth.utils.EmailAuthUtils
import com.smlnskgmail.jaman.randomnotes.parse.auth.utils.FacebookAuthUtils

class ParseAuth(fragment: Fragment, private val authCallback: AuthCallback?) {

    companion object {

        fun isAuthorized() = ParseUser.getCurrentUser() != null

        fun initialize(context: Context) {
            ParseFacebookUtils.initialize(context)
        }

    }

    private var emailAuthUtils: EmailAuthUtils = EmailAuthUtils()
    private var facebookAuthUtils: FacebookAuthUtils = FacebookAuthUtils(fragment)

    fun logInWithEmail(username: String, password: String) {
        emailAuthUtils.logIn(username, password) {
            setAuthResult(it, AuthStatus.EMAIL_LOGIN_SUCCESS, AuthStatus.EMAIL_LOGIN_ERROR)
        }
    }

    fun registerWithEmail(username: String, email: String, password: String) {
        emailAuthUtils.register(username, email, password) {
            setAuthResult(it, AuthStatus.EMAIL_REGISTER_SUCCESS, AuthStatus.EMAIL_REGISTER_ERROR)
        }
    }

    fun logInWithFacebook() {
        facebookAuthUtils.continueWithFacebook {
            setAuthResult(it, AuthStatus.FACEBOOK_SUCCESS, AuthStatus.FACEBOOK_ERROR)
        }
    }

    fun logOut() {
        val parseUser = ParseUser.getCurrentUser()
        if (facebookAuthUtils.isLinked(parseUser)) {
            facebookAuthUtils.logOut()
        }
        ParseUser.logOutInBackground {
            setAuthResult(it, AuthStatus.LOGOUT_SUCCESS, AuthStatus.LOGOUT_ERROR)
        }
    }

    private fun setAuthResult(e: Exception?, doneStatus: AuthStatus, errorStatus: AuthStatus) {
        if (e == null) {
            authCallback!!.onAuthResult(doneStatus)
        } else {
            authCallback!!.onAuthResult(errorStatus)
        }
    }

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookAuthUtils.handleOnActivityResult(requestCode, resultCode, data)
    }

}