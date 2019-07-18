package com.smlnskgmail.jaman.randomnotes.navigation

import android.view.View
import com.parse.ParseUser
import com.smlnskgmail.jaman.randomnotes.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private var loginMode = true

    override fun initialize() {
        register_account.setOnClickListener {
            if (loginMode) {
                login_action.text = "Sing up"
                register_account.text = "I have an account"
                email.visibility = View.VISIBLE
            } else {
                login_action.text = "Login"
                register_account.text = "Not account yet? Create one"
                email.visibility = View.GONE
            }
            loginMode = !loginMode
        }
        login_action.setOnClickListener {
            if (loginMode) {
                ParseUser.logIn(username.text.toString(), password.text.toString())
            } else {
                val parseUser = ParseUser()
                parseUser.username = username.text.toString()
                parseUser.email = email.text.toString()
                parseUser.setPassword(password.text.toString())
                parseUser.signUpInBackground()
            }
        }
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun getLayoutResId() = R.layout.fragment_login

}