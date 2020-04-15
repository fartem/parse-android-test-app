package com.smlnskgmail.jaman.randomnotes.auth.verification.parse

import com.smlnskgmail.jaman.randomnotes.auth.verification.BaseAuthEmailVerificationTest
import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudAuth
import com.smlnskgmail.jaman.randomnotes.model.impl.cloud.parse.ParseServerAuth

class ParseServerAuthPasswordVerificationTest : BaseAuthEmailVerificationTest() {

    override fun cloudAuth(): CloudAuth {
        return ParseServerAuth()
    }

}
