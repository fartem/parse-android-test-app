package com.smlnskgmail.jaman.randomnotes.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.smlnskgmail.jaman.randomnotes.App
import com.smlnskgmail.jaman.randomnotes.MainActivity
import com.smlnskgmail.jaman.randomnotes.R
import com.smlnskgmail.jaman.randomnotes.components.BaseFragment
import com.smlnskgmail.jaman.randomnotes.components.LongSnackbar
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.presenter.auth.CloudAuthPresenter
import com.smlnskgmail.jaman.randomnotes.presenter.auth.CloudAuthPresenterImpl
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

class CloudAuthFragment : BaseFragment(), CloudAuthView {

    @Inject
    lateinit var cloudAuth: CloudAuth

    private lateinit var cloudAuthPresenter: CloudAuthPresenter

    private var isSignInMode = true

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        (context!!.applicationContext as App).appComponent.inject(this)

        cloudAuthPresenter = CloudAuthPresenterImpl()
        cloudAuthPresenter.init(
            cloudAuth,
            this
        )

        register_account.setOnClickListener {
            changeAuthMode()
        }
        auth_action.setOnClickListener {
            actionWithEmail()
        }
        google_sign_in.setOnClickListener {
            cloudAuthPresenter.signInWithGoogle(
                activity!!
            )
        }
        facebook_sign_in.setOnClickListener {
            cloudAuthPresenter.signInWithFacebook(
                activity!!
            )
        }
    }

    override fun showHomeAsUpEnabled(): Boolean {
        return true
    }

    private fun changeAuthMode() {
        if (isSignInMode) {
            auth_action.text = getString(R.string.action_sign_up)
            register_account.text = getString(R.string.message_have_account)
        } else {
            auth_action.text = getString(R.string.action_sign_in)
            register_account.text = getString(R.string.message_not_account)
        }
        isSignInMode = !isSignInMode
    }

    override fun authSuccess() {
        (activity as MainActivity).showNotesListFragment()
    }

    override fun authError() {
        LongSnackbar(
            auth_screen,
            getString(R.string.error_auth)
        ).show()
    }

    private fun actionWithEmail() {
        val email = email.text.toString()
        if (!cloudAuth.isValidEmail(email)) {
            LongSnackbar(
                auth_screen,
                getString(R.string.message_incorrect_email_format)
            ).show()
            return
        }
        val password = password.text.toString()
        if (!cloudAuth.isValidPassword(password)) {
            LongSnackbar(
                auth_screen,
                getString(R.string.message_incorrect_password_length).format(
                    cloudAuth.passwordMinimumLength(),
                    cloudAuth.passwordMaximumLength()
                )
            ).show()
            return
        }
        if (isSignInMode) {
            cloudAuthPresenter.signInWithEmail(
                email,
                password
            )
        } else {
            cloudAuthPresenter.signUpWithEmail(
                email,
                password
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        cloudAuthPresenter.handleSocialAuthRequest(
            requestCode,
            resultCode,
            data
        )
    }

    override fun getTitleResId() = R.string.title_auth_fragment

    override fun showToolbarMenu() = false

    override fun layoutResId() = R.layout.fragment_auth

    override fun showMenuInToolbar() = false

}
