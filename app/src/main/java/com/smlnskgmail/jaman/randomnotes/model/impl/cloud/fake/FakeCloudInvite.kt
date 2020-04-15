package com.smlnskgmail.jaman.randomnotes.model.impl.cloud.fake

import com.smlnskgmail.jaman.randomnotes.model.api.cloud.CloudInvite

class FakeCloudInvite : CloudInvite {

    override fun invite(
        email: String,
        inviteResult: (e: Exception?) -> Unit
    ) {
        inviteResult(null)
    }

}
