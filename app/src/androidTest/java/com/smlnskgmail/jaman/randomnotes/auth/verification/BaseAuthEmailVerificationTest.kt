package com.smlnskgmail.jaman.randomnotes.auth.verification

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

abstract class BaseAuthEmailVerificationTest {

    fun context(): Context {
        return InstrumentationRegistry.getInstrumentation().context
    }

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
