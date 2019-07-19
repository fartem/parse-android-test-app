package com.smlnskgmail.jaman.randomnotes.navigation

import android.content.Intent
import android.view.View
import com.parse.ParseUser
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import kotlinx.android.synthetic.main.fragment_login.*

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
                    } else {
                        e.printStackTrace()
                    }
                }
            } else {
                val parseUser = ParseUser()
                parseUser.username = username.text.toString()
                parseUser.email = email.text.toString()
                parseUser.setPassword(password.text.toString())
                parseUser.signUpInBackground {
                    (activity as MainActivity).loginComplete()
                }
            }
        }
        facebook_login.setOnClickListener {
            ParseFacebookUtils.initialize(context)
            ParseFacebookUtils.logInWithReadPermissionsInBackground(this,
                listOf("public_profile", "email")) { _, e ->
                if (e == null) {
                    (activity as MainActivity).loginComplete()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun showToolbarMenu() = false

    override fun getLayoutResId() = R.layout.fragment_login

}