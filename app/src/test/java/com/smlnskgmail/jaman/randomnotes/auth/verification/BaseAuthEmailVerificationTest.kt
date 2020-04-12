package com.smlnskgmail.jaman.randomnotes.auth.verification

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

abstract class BaseAuthEmailVerificationTest {

    @Test
    fun validEmailAddressTest() {
        assertTrue(
            cloudAuth().isValidEmail(
                "artem.fomchenkov@outlook.com"
            )
        )
    }

    abstract fun cloudAuth(): CloudAuth

    @Test
    fun invalidEmailAddressTest() {
        assertFalse(
            cloudAuth().isValidEmail(
                "email_.com"
            )
        )
    }

}
