package com.smlnskgmail.jaman.randomnotes.logic.repository.impl.cloud.parse

import com.parse.FunctionCallback
import com.parse.ParseCloud
import com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud.CloudInvite

class ParseServerInvite : CloudInvite {

    override fun invite(
        email: String,
        inviteResult: (e: Exception?) -> Unit
    ) {
        ParseCloud.callFunctionInBackground(
            "invite",
            hashMapOf("email" to email),
            FunctionCallback<Boolean> { _, e ->
                inviteResult(e)
            }
        )
    }

}
