package com.smlnskgmail.jaman.randomnotes.logic.repository.api.cloud

interface CloudInvite {

    fun invite(
        email: String,
        inviteResult: (e: Exception?) -> Unit
    )

}
