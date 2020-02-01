package com.smlnskgmail.jaman.randomnotes.logic.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.smlnskgmail.jaman.randomnotes.Application
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.fragments.BaseFragment
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.CloudAuth
import com.smlnskgmail.jaman.randomnotes.tools.L
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    private var loginMode = true

    @Inject
    lateinit var cloudAuth: CloudAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Application.applicationComponent.inject(this)
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
            cloudAuth.signInWithEmail(username, password) {
                handleLoginResult(it)
            }
        } else {
            cloudAuth.signUpWithEmail(username, username, password) {
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
        cloudAuth.signInWithFacebook(this) {
            handleLoginResult(it)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        cloudAuth.bindForAuth(requestCode, resultCode, data)
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun showToolbarMenu() = false

    override fun getLayoutResId() = R.layout.fragment_login

    override fun showMenuInToolbar() = false

}
