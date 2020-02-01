package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.parse

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.CloudAuth

class ParseAuth :
    CloudAuth {

    override fun isAuthorized(): Boolean {
        return ParseUser.getCurrentUser() != null
    }

    override fun signInWithFacebook(
        fragment: Fragment,
        afterFacebookLogin: (e: Exception) -> Unit
    ) {
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
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
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

}
