package com.smlnskgmail.jaman.randomnotes.navigation

import android.content.Intent
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private var loginMode = true

    override fun initialize() {
        register_account.setOnClickListener {
            register()
        }
        login_action.setOnClickListener {
            loginWithEmail()
        }
        facebook_login.setOnClickListener {
            loginWithFacebook()
        }
    }

    private fun register() {
        if (loginMode) {
            login_action.text = getString(R.string.action_sign_up)
            register_account.text = getString(R.string.message_have_account)
        } else {
            login_action.text = getString(R.string.action_login)
            register_account.text = getString(R.string.message_not_account)
        }
        loginMode = !loginMode
    }

    private fun loginWithEmail() {
        if (loginMode) {
            ParseUser.logInInBackground(email.text.toString(), password.text.toString()) { user, e ->
                if (user != null) {
                    (activity as MainActivity).loginComplete()
                } else if (e != null) {
                    signInError(e)
                }
            }
        } else {
            val parseUser = ParseUser()
            parseUser.username = email.text.toString()
            parseUser.email = email.text.toString()
            parseUser.setPassword(password.text.toString())
            parseUser.signUpInBackground {
                if (it == null) {
                    (activity as MainActivity).loginComplete()
                } else {
                    signInError(it)
                }
            }
        }
    }

    private fun loginWithFacebook() {
        val emailField = "email"
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this,
            listOf("public_profile", emailField)) { user, e ->
            if (user != null) {
                if (user.email == null) {
                    val graphRequest = GraphRequest.newMeRequest(
                        AccessToken
                            .getCurrentAccessToken()) { data, response ->
                        if (data != null) {
                            user.email = data.getString(emailField)
                            user.saveInBackground {
                                if (it == null) {
                                    (activity as MainActivity).loginComplete()
                                } else {
                                    signInError(it)
                                }
                            }
                        } else {
                            (activity as MainActivity).loginError()
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", emailField)
                    graphRequest.parameters = parameters
                    graphRequest.executeAsync()
                } else {
                    (activity as MainActivity).loginComplete()
                }
            } else if (e != null) {
                signInError(e)
            }
        }
    }

    private fun signInError(e: Exception) {
        UIUtils.toast(context!!, getString(R.string.error_sign_up))
        log(e)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun showToolbarMenu() = false

    override fun getLayoutResId() = R.layout.fragment_login

}