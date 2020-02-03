package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake

import android.app.Activity
import android.content.Intent
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth

class FakeCloudAuth : CloudAuth {

    override fun isAuthorized(): Boolean {
        return true
    }

    override fun logInWithFacebook(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    ) {
        afterFacebookLogin(
            IllegalStateException(
                "Fake impl cannot support Facebook auth!"
            )
        )
    }

    override fun logInWithGoogle(
        activity: Activity,
        afterFacebookLogin: (e: Exception?) -> Unit
    ) {
        afterFacebookLogin(
            IllegalStateException(
                "Fake impl cannot support Google auth!"
            )
        )
    }

    override fun signUpWithEmail(
        username: String,
        email: String,
        password: String,
        afterLogin: (e: Exception?) -> Unit
    ) {
        afterLogin(
            IllegalStateException(
                "Fake impl cannot support email auth!"
            )
        )
    }

    override fun signInWithEmail(
        username: String,
        password: String,
        afterRegister: (e: Exception?) -> Unit
    ) {
        afterRegister(
            IllegalStateException(
                "Fake impl cannot support email auth!"
            )
        )
    }

    override fun bindForAuth(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun logOut(afterLogOut: (e: Exception?) -> Unit) {
        afterLogOut(
            IllegalStateException(
                "Fake impl cannot support log out!"
            )
        )
    }

}
