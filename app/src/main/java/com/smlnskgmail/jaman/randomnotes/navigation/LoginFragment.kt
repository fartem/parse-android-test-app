package com.smlnskgmail.jaman.randomnotes.navigation

import android.content.Intent
import android.util.Log
import com.parse.facebook.ParseFacebookUtils
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.ui.LongToast
import com.smlnskgmail.jaman.randomnotes.parse.ParseApi
import kotlinx.android.synthetic.main.fragment_login.*
import java.lang.Exception

class LoginFragment : BaseFragment() {

    private var loginMode = true

    override fun onViewCreated() {
        super.onViewCreated()
        setupActions()
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
            ParseApi.register(username, password) {
                verifyLogin(it)
            }
        } else {
            ParseApi.loginWithEmail(username, username, password) {
                verifyLogin(it)
            }
        }
    }

    private fun loginWithFacebook() {
        ParseApi.loginWithFacebook(this) {
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
        super.onActivityResult(requestCode, resultCode, data)
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data)
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun showToolbarMenu() = false

    override fun getLayoutResId() = R.layout.fragment_login

    override fun showMenuInToolbar() = false

}