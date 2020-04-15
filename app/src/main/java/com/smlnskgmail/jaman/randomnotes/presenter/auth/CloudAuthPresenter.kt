package com.smlnskgmail.jaman.randomnotes.presenter.auth

import android.app.Activity
import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.view.auth.CloudAuthView

interface CloudAuthPresenter {

    fun init(
        cloudAuth: CloudAuth,
        cloudAuthView: CloudAuthView
    )

    fun signUpWithEmail(
        email: String,
        password: String
    )
    fun logInWithEmail(
        email: String,
        password: String
    )

    fun logInWithGoogle(
        activity: Activity
    )
    fun logInWithFacebook(
        activity: Activity
    )

    fun handleSocialAuthRequest(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )

}
