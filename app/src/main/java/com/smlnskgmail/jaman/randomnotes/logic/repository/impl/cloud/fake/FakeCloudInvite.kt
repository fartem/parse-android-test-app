package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.fake

import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudInvite

class FakeCloudInvite : CloudInvite {

    override fun invite(
        email: String,
        inviteResult: (e: Exception?) -> Unit
    ) {
        inviteResult(null)
    }

}
