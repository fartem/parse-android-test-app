package com.smlnskgmail.jaman.randomnotes.navigation

import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.logger.L
import com.smlnskgmail.jaman.randomnotes.repository.DataRepositoryAccessor
import kotlinx.android.synthetic.main.fragment_login.*

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
            DataRepositoryAccessor.get().signInWithEmail(username, password) {
                handleLoginResult(it)
            }
        } else {
            DataRepositoryAccessor.get().signUpWithEmail(username, username, password) {
                handleLoginResult(it)
            }
        }
    }

    private fun handleLoginResult(exception: Exception?) {
        if (exception == null) {
            (activity as MainActivity).loginComplete()
        } else {
            L.e(exception)
            (activity as MainActivity).loginError()
        }
    }

    private fun loginWithFacebook() {
        DataRepositoryAccessor.get().signInWithFacebook(this) {
            handleLoginResult(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        DataRepositoryAccessor.get().bindForAuth(requestCode, resultCode, data)
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun showToolbarMenu() = false

    override fun getLayoutResId() = R.layout.fragment_login

    override fun showMenuInToolbar() = false

}