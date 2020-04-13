package com.smlnskgmail.jaman.randomnotes.logic.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.fragments.BaseFragment
import com.smlnskgmail.jaman.randomnotes.components.views.LongSnackbar
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.logic.support.L
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    private var loginMode = true

    @Inject
    lateinit var cloudAuth: CloudAuth

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        (context!!.applicationContext as App).appComponent.inject(this)

        register_account.setOnClickListener {
            changeLoginMode()
        }
        login_action.setOnClickListener {
            actionWithEmail()
        }
        google_login.setOnClickListener {
            loginWithGoogle()
        }
        facebook_login.setOnClickListener {
            loginWithFacebook()
        }
    }

    private fun changeLoginMode() {
        if (loginMode) {
            login_action.text = getString(R.string.action_sign_up)
            register_account.text = getString(R.string.message_have_account)
        } else {
            login_action.text = getString(R.string.action_login)
            register_account.text = getString(R.string.message_not_account)
        }
        loginMode = !loginMode
    }

    private fun actionWithEmail() {
        val email = email.text.toString()
        if (!cloudAuth.isValidEmail(email)) {
            LongSnackbar(
                login_screen,
                getString(R.string.message_incorrect_email_format)
            ).show()
            return
        }
        val password = password.text.toString()
        if (!cloudAuth.isValidPassword(password)) {
            LongSnackbar(
                login_screen,
                getString(R.string.message_incorrect_password_length).format(
                    cloudAuth.passwordMinimumLength(),
                    cloudAuth.passwordMaximumLength()
                )
            ).show()
            return
        }
        if (loginMode) {
            cloudAuth.signInWithEmail(
                email,
                password
            ) {
                handleLoginResult(it)
            }
        } else {
            cloudAuth.signUpWithEmail(
                email,
                email,
                password
            ) {
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

    private fun loginWithGoogle() {
        cloudAuth.logInWithGoogle(activity!!) {
            handleLoginResult(it)
        }
    }

    private fun loginWithFacebook() {
        cloudAuth.logInWithFacebook(activity!!) {
            handleLoginResult(it)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        cloudAuth.bindForAuth(
            requestCode,
            resultCode,
            data
        )
    }

    override fun getTitleResId() = R.string.title_login_fragment

    override fun showToolbarMenu() = false

    override fun layoutResId() = R.layout.fragment_login

    override fun showMenuInToolbar() = false

}
