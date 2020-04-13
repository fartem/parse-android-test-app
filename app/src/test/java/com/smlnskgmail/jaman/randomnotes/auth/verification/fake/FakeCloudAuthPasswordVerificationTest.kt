package com.smlnskgmail.jaman.randomnotes.auth.verification.fake

import com.smlnskgmail.jaman.randomnotes.auth.verification.BaseAuthPasswordVerificationTest
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake.FakeCloudAuth

class FakeCloudAuthPasswordVerificationTest : BaseAuthPasswordVerificationTest() {

    override fun cloudAuth(): CloudAuth {
        return FakeCloudAuth()
    }

}
