package com.smlnskgmail.jaman.randomnotes.model.api.cloud

interface CloudInvite {

    fun invite(
        email: String,
        inviteResult: (success: Boolean) -> Unit
    )

}
