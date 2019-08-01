package com.smlnskgmail.jaman.randomnotes.parse.auth.utils

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils

class FacebookAuthUtils(private val fragment: Fragment) {

    fun continueWithFacebook(afterFacebookLogin: (e: Exception) -> Unit) {
        val emailField = "email"
        ParseFacebookUtils.logInWithReadPermissionsInBackground(fragment,
            listOf("public_profile", emailField)) { user, e ->
            if (user != null) {
                if (user.email == null) {
                    val graphRequest = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken()) { data, response ->
                        if (data != null) {
                            user.email = data.getString(emailField)
                            user.saveInBackground {
                                afterFacebookLogin(it)
                            }
                        } else {
                            afterFacebookLogin(e)
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", emailField)
                    graphRequest.parameters = parameters
                    graphRequest.executeAsync()
                } else {
                    afterFacebookLogin(e)
                }
            } else if (e != null) {
                afterFacebookLogin(e)
            }
        }
    }

    fun logOut() {
        AccessToken.setCurrentAccessToken(null)
        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut()
        }
    }

    fun isLinked(parseUser: ParseUser) = ParseFacebookUtils.isLinked(parseUser)

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
    }

}