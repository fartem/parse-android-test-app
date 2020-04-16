package com.smlnskgmail.jaman.randomnotes.presenter.auth

import android.app.Activity
import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.view.auth.CloudAuthView

class CloudAuthPresenterImpl : CloudAuthPresenter {

    private lateinit var cloudAuth: CloudAuth
    private lateinit var cloudAuthView: CloudAuthView

    override fun init(
        cloudAuth: CloudAuth,
        cloudAuthView: CloudAuthView
    ) {
        this.cloudAuth = cloudAuth
        this.cloudAuthView = cloudAuthView
    }

    override fun signUpWithEmail(
        email: String,
        password: String
    ) {
        cloudAuth.signUpWithEmail(
            email,
            email,
            password
        ) { handleAuthResult(it) }
    }

    private fun handleAuthResult(
        e: Exception?
    ) {
        if (e == null) {
            cloudAuthView.authSuccess()
        } else {
            cloudAuthView.authError()
        }
    }

    override fun signInWithEmail(
        email: String,
        password: String
    ) {
        cloudAuth.signInWithEmail(
            email,
            password
        ) { handleAuthResult(it) }
    }

    override fun signInWithGoogle(
        activity: Activity
    ) {
        cloudAuth.signInWithGoogle(
            activity
        ) { handleAuthResult(it) }
    }

    override fun signInWithFacebook(
        activity: Activity
    ) {
        cloudAuth.signInWithFacebook(
            activity
        ) { handleAuthResult(it) }
    }

    override fun handleSocialAuthRequest(
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

}
