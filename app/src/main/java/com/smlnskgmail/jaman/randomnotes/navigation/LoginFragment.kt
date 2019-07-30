package com.smlnskgmail.jaman.randomnotes.navigation

import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.support.LongToast
import com.smlnskgmail.jaman.randomnotes.parse.ParseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private lateinit var parseAuth: ParseAuth

    private var loginMode = true

    override fun onViewCreated() {
        super.onViewCreated()
        initializeParseAuth()
        setupActions()
    }

    private fun initializeParseAuth() {
        parseAuth = ParseAuth()
    }

    private fun setupActions() {
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
        val username = email.text.toString()
        val password = password.text.toString()
        if (loginMode) {
            parseAuth.logInWithEmail(username, password) {
                verifyLogin(it)
            }
        } else {
            parseAuth.register(username, username, password) {
                verifyLogin(it)
            }
        }
    }

    private fun loginWithFacebook() {
        parseAuth.logInWithFacebook(this) {
            if (it) {
                (activity as MainActivity).loginComplete()
            } else {
                (activity as MainActivity).loginError()
            }
        }
    }

    private fun verifyLogin(e: Exception?) {
        if (e == null) {
            (activity as MainActivity).loginComplete()
        } else {
            LongToast.show(context!!, getString(R.string.error_sign_up))
            log(e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        parseAuth.handleOnActivityResult(requestCode, resultCode, data)
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun showToolbarMenu() = false

    override fun getLayoutResId() = R.layout.fragment_login

    override fun showMenuInToolbar() = false

}