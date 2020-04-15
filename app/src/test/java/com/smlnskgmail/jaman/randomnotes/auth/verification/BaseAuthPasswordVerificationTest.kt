package com.smlnskgmail.jaman.randomnotes.auth.verification

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import org.junit.Assert
import org.junit.Test

abstract class BaseAuthPasswordVerificationTest {

    @Test
    fun validPasswordTest() {
        Assert.assertTrue(
            cloudAuth().isValidPassword(
                "sadfasdf1341fwsfnsdflksd"
            )
        )
    }

    abstract fun cloudAuth(): CloudAuth

    @Test
    fun invalidPasswordTest() {
        Assert.assertFalse(
            cloudAuth().isValidPassword(
                "qwerty"
            )
        )
    }

}
