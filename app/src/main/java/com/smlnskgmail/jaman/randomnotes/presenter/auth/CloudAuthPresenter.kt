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
    fun signInWithEmail(
        email: String,
        password: String
    )

    fun signInWithGoogle(
        activity: Activity
    )
    fun signInWithFacebook(
        activity: Activity
    )

    fun handleSocialAuthRequest(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    )

}
