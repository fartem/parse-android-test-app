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
        ) { success -> handleAuthResult(success) }
    }

    private fun handleAuthResult(
        success: Boolean
    ) {
        if (success) {
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
        ) { success -> handleAuthResult(success) }
    }

    override fun signInWithGoogle(
        activity: Activity
    ) {
        cloudAuth.signInWithGoogle(
            activity
        ) { success -> handleAuthResult(success) }
    }

    override fun signInWithFacebook(
        activity: Activity
    ) {
        cloudAuth.signInWithFacebook(
            activity
        ) { success -> handleAuthResult(success) }
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
