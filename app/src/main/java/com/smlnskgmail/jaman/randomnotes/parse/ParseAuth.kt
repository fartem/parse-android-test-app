package com.smlnskgmail.jaman.randomnotes.parse

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils

class ParseAuth {

    companion object {

        fun isAuthorized() = ParseUser.getCurrentUser() != null

        fun logout(afterLogout: (e: Exception) -> Unit) {
            val parseUser = ParseUser.getCurrentUser()
            if (ParseFacebookUtils.isLinked(parseUser)) {
                AccessToken.setCurrentAccessToken(null)
                if (LoginManager.getInstance() != null) {
                    LoginManager.getInstance().logOut()
                }
            }
            ParseUser.logOutInBackground {
                afterLogout(it)
            }
        }

    }

    fun logInWithEmail(username: String, password: String, afterRegister: (e: Exception?) -> Unit) {
        ParseUser.logInInBackground(username, password) { user, e ->
            afterRegister(e)
        }
    }

    fun register(username: String, email: String, password: String,
                 afterLogin: (e: Exception?) -> Unit) {
        val parseUser = ParseUser()
        parseUser.username = username
        parseUser.email = email
        parseUser.setPassword(password)
        parseUser.signUpInBackground {
            afterLogin(it)
        }
    }

    fun logInWithFacebook(fragment: Fragment, afterFacebookLogin: (success: Boolean) -> Unit) {
        val emailField = "email"
        ParseFacebookUtils.logInWithReadPermissionsInBackground(fragment,
            listOf("public_profile", emailField)) { user, e ->
            if (user != null) {
                if (user.email == null) {
                    val graphRequest = GraphRequest.newMeRequest(
                        AccessToken
                            .getCurrentAccessToken()) { data, response ->
                        if (data != null) {
                            user.email = data.getString(emailField)
                            user.saveInBackground {
                                afterFacebookLogin(it == null)
                            }
                        } else {
                            afterFacebookLogin(false)
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", emailField)
                    graphRequest.parameters = parameters
                    graphRequest.executeAsync()
                } else {
                    afterFacebookLogin(false)
                }
            } else if (e != null) {
                afterFacebookLogin(false)
            }
        }
    }

    fun handleOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
    }

}