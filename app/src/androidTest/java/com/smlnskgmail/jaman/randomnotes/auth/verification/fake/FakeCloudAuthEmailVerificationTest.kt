package com.smlnskgmail.jaman.randomnotes.auth.verification.fake

import com.smlnskgmail.jaman.randomnotes.auth.verification.BaseAuthEmailVerificationTest
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake.FakeCloudAuth

class FakeCloudAuthEmailVerificationTest : BaseAuthEmailVerificationTest() {

    override fun cloudAuth(): CloudAuth {
        return FakeCloudAuth()
    }

}
