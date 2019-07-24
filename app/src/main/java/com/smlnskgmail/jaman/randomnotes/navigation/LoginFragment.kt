package com.smlnskgmail.jaman.randomnotes.navigation

import android.content.Intent
import android.view.View
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.navigation.BaseFragment
import com.smlnskgmail.jaman.randomnotes.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_login.*
import android.os.Bundle

class LoginFragment : BaseFragment() {

    private var loginMode = true

    override fun initialize() {
        register_account.setOnClickListener {
            if (loginMode) {
                login_action.text = getString(R.string.action_sign_up)
                register_account.text = getString(R.string.message_have_account)
                email.visibility = View.VISIBLE
            } else {
                login_action.text = getString(R.string.action_login)
                register_account.text = getString(R.string.message_not_account)
                email.visibility = View.GONE
            }
            loginMode = !loginMode
        }
        login_action.setOnClickListener {
            if (loginMode) {
                ParseUser.logInInBackground(username.text.toString(), password.text.toString()) { user, e ->
                    if (user != null) {
                        (activity as MainActivity).loginComplete()
                    } else if (e != null) {
                        signInError(e)
                    }
                }
            } else {
                val parseUser = ParseUser()
                parseUser.username = username.text.toString()
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
        facebook_login.setOnClickListener {
            ParseFacebookUtils.logInWithReadPermissionsInBackground(this,
                listOf("public_profile", "email")) { user, e ->
                if (user != null) {
                    if (user.email == null) {
                        val graphRequest = GraphRequest.newMeRequest(AccessToken
                            .getCurrentAccessToken()) { data, response ->
                            if (data != null) {
                                user.email = data.getString("email")
                                user.saveInBackground {
                                    if (it == null) {
                                        (activity as MainActivity).loginComplete()
                                    }
                                }
                            } else {
                                (activity as MainActivity).loginError()
                            }
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "email")
                        graphRequest.parameters = parameters
                        graphRequest.executeAsync()
                    }
                } else if (e != null) {
                    signInError(e)
                }
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